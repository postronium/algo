package s13;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//======================================================================
public class LempelZiv {
    //======================================================================
    static class LzvCodeTable {
        private ArrayList<byte[]> table;

        // --------------------------------------------
        public LzvCodeTable() {
            table = new ArrayList<byte[]>();
            table.add(new byte[0]);  // dummy "empty string" entry
        }

        // --------------------------------------------
        private int lookup(byte[] s) {
            for (int i = 0; i < table.size(); i++)
                if (Arrays.equals(s, table.get(i))) return i;
            return -1;
        }

        // --------------------------------------------
        public int size() {
            return table.size();
        }

        // --------------------------------------------
        public boolean contains(byte[] s) {
            if (s.length == 0) return true;
            return (lookup(s) != -1);
        }

        // --------------------------------------------
        public int putAndCode(byte[] s, byte c) {
            int i = lookup(s);
            byte[] z = addToByteArray(s, c);
            table.add(z);
            return i;
        }

        // --------------------------------------------
        public byte[] putAndDecode(int index, byte c) {
            byte[] s = table.get(index);
            byte[] z = addToByteArray(s, c);
            table.add(z);
            return z;
        }

        // --------------------------------------------
        public String toString() {
            String s = "";
            for (int i = 0; i < table.size(); i++) {
                byte[] b = table.get(i);
                String w = "";
                for (byte c : b) w += Character.isLetterOrDigit((char) c) ? (char) c : "-";
                s += "" + i + ": " + Arrays.toString(b) + " \"" + w + "\"\n";
            }
            return s;
        }
    }

    //======================================================================
    static class LzvItem {
        final int entryNb;
        final byte appendedChar;

        public LzvItem(int entryNb, byte appendedChar) {
            this.entryNb = entryNb;
            this.appendedChar = appendedChar;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || this.getClass() != o.getClass()) return false;
            LzvItem oo = (LzvItem) o;
            return oo.entryNb == entryNb && oo.appendedChar == appendedChar;
        }
    }

    // ------------------------------------------------------------
    // ------------------------------------------------------------
    // ------------------------------------------------------------
    public static void code(String infile, String outfile) throws IOException {
        FileInputStream bis = new FileInputStream(infile);
        FileOutputStream bos = new FileOutputStream(outfile);
        PrintWriter tos = new PrintWriter(new FileWriter(outfile + ".txt"));
        LzvCodeTable t = new LzvCodeTable();
        byte crt = (byte) bis.read();
        byte[] prefix = new byte[0];
        while (crt != -1) {

            // If prefix does not exist empty prefix is used
            byte[] newPref = new byte[prefix.length + 1];
            int i;
            for(i = 0; i < prefix.length; i ++){
                newPref[i] = prefix[i];
            }
            newPref[i] = crt;

            prefix = newPref;
            if(t.contains(newPref)){
                continue;
            }


            int index = t.putAndCode(prefix, crt);
            LzvItem item = new LzvItem(index, crt);

            printItem(bos, tos, item);
            // Reading next byte
            crt = (byte) bis.read();
            // TODO - A COMPLETER
            // ...
            // ...  LzvItem item = new LzvItem(...
            // ...  printItem(bos, tos, item);
            // ...
            // Remember there are two cases where EOF can happen:
            // - just after a fresh item;
            // - with a current word that forms a known prefix; in this case
            //   we will add again this word in the code table.
        }
        bis.close();
        bos.close();
        tos.close();
        System.out.println("Code table: size = " + t.size());
        if (t.size() < 10) {
            System.out.println("Table Content:");
            System.out.println(t);
        }
    }

    // ------------------------------------------------------------
    public static void decode(String infile, String outfile) throws IOException {
        FileInputStream bis = new FileInputStream(infile);
        Scanner tis = new Scanner(new FileReader(infile + ".txt"));
        FileOutputStream bos = new FileOutputStream(outfile);
        LzvCodeTable t = new LzvCodeTable();
        LzvItem item = readItem(bis, tis);
        while (item != null) {
            byte[] s = t.putAndDecode(item.entryNb, item.appendedChar);
            bos.write(s);
            item = readItem(bis, tis);
        }
        bis.close();
        tis.close();
        bos.close();
        System.out.println("Code table: size = " + t.size());
        if (t.size() < 10) {
            System.out.println("Table Content:");
            System.out.println(t);
        }
    }

    // ------------------------------------------------------------
    private static byte[] addToByteArray(byte[] t, byte b) {
        byte[] res = new byte[t.length + 1];
        System.arraycopy(t, 0, res, 0, t.length);
        res[res.length - 1] = b;
        return res;
    }

    // ------------------------------------------------------------
    private static void printItem(FileOutputStream bos,
                                  PrintWriter tos,
                                  LzvItem item) throws IOException {
        printItemAsBinary(bos, item);  // really compressed!
        printItemAsText(tos, item);  // useful for debugging
    }

    // ------------------------------------------------------------
    private static void printItemAsBinary(FileOutputStream bos, LzvItem item) throws IOException {
        byte[] entryNb = new byte[2];
        entryNb[0] = (byte)(item.entryNb & (0xff << 8));
        entryNb[1] = (byte)(item.entryNb & (0xff));
        bos.write(entryNb);
        bos.write(item.appendedChar);
        bos.flush();
    }

    // ------------------------------------------------------------
    private static void printItemAsText(PrintWriter tos, LzvItem item) throws IOException {
        tos.print(" " + item.entryNb);
        tos.print(" " + item.appendedChar);
        char c = (char) item.appendedChar;
        if (Character.isLetterOrDigit(c))
            tos.print(" \"" + c + "\"");
        else
            tos.print(" " + "-");
        tos.println();
    }

    // ------------------------------------------------------------
    // ------------------------------------------------------------
    private static LzvItem readItem(FileInputStream bis, Scanner tis) throws IOException {
        LzvItem res = readItemFromBinary(bis);
        LzvItem res1 = readItemFromText(tis);
        if (res == null && res1 == null) return res;
        if ((res == null ^ res1 == null) || !res1.equals(res)) {
            System.out.println("Oups... binary and text formats don't agree!");
            System.exit(-1);
        }
        return res;
    }
    // ------------------------------------------------------------

    /**
     * returns null when there is no item (EOF)
     */
    private static LzvItem readItemFromBinary(FileInputStream bis) throws IOException {
        if(bis.available() < 2) return null;
        byte[] r = new byte[2];
        bis.read(r);
        return new LzvItem(r[0], r[1]);
    }

    // ------------------------------------------------------------
    private static LzvItem readItemFromText(Scanner tis) throws IOException {
        if (!tis.hasNext()) return null;
        int code = tis.nextInt();
        byte crtByte = tis.nextByte();
        tis.next();  // dummy character
        LzvItem res = new LzvItem(code, crtByte);
        return res;
    }

    // ------------------------------------------------------------
    // ------------------------------------------------------------
    // ------------------------------------------------------------
    public static void main(String[] args) {
        if (args.length != 3) usage();
        String cmd = args[0];
        try {
            if (cmd.equals("code"))
                code(args[1], args[2]);
            else if (cmd.equals("decode"))
                decode(args[1], args[2]);
            else
                usage();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------
    private static void usage() {
        System.out.println("Usage: LempelZiv code   infile outfile");
        System.out.println("   or: LempelZiv decode infile outfile");
        System.exit(-1);
    }
}