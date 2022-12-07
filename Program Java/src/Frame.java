import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.awt.BorderLayout;

public class Frame extends JFrame implements ActionListener{

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    JTextField pText = new JTextField(),
               key = new JTextField();

    JComboBox cipherSelection;

    JButton enkrip = new JButton("Encrypt"),
            dekrip = new JButton("Decrypt"),
            clear = new JButton("Clear");

    JTextArea hasil = new JTextArea();

    public Frame(){
        this.setSize(new Dimension(750,570));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.setBackground(Color.black);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.gray);
        titlePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        JPanel main = new JPanel();

        JLabel titleLabel = new JLabel("Kalkulator Vigenere Chiper dan Autokey Cipher");
        titleLabel.setFont(new Font("Sans",Font.BOLD,30));
        titleLabel.setForeground(Color.white);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titlePanel.add(titleLabel);

        JLabel text = new JLabel("Text");
        text.setPreferredSize(new Dimension(600,20));
        main.add(text);
        pText.setPreferredSize(new Dimension(600,20));
        main.add(pText);

        JLabel kunci = new JLabel("Key");
        kunci.setPreferredSize(new Dimension(600,20));
        main.add(kunci);
        key.setPreferredSize(new Dimension(600,20));
        main.add(key);

        String listCipher[] = {"Autokey", "Vigenere", "Autokey-Vigenere", "Vigenere-Autokey"};
        cipherSelection = new JComboBox<>(listCipher);
        cipherSelection.setPreferredSize(new Dimension(600,40));
        main.add(cipherSelection);
        JLabel jumpLine = new JLabel();
        jumpLine.setPreferredSize(new Dimension(130,20));
        main.add(jumpLine);
        enkrip.addActionListener(this);
        main.add(enkrip);
        dekrip.addActionListener(this);
        main.add(dekrip);
        clear.addActionListener(this);
        main.add(clear);

        JLabel hasilLabel = new JLabel("Hasil");
        hasilLabel.setPreferredSize(new Dimension(600,20));
        hasil.setPreferredSize(new Dimension(600,200));
        main.add(hasilLabel);
        hasil.setEditable(false);
        main.add(hasil);

        this.add(titlePanel,BorderLayout.NORTH);
        this.add(main,BorderLayout.CENTER);
        
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String plainText = pText.getText();
        String keyword = key.getText();
        String out;

        if (e.getSource() == enkrip) {
            if (cipherSelection.getSelectedItem().equals("Autokey")){
                if (hasil.getText() == "") {
                    hasil.append(autoEncryption(plainText, keyword));
                }else{
                    hasil.setText("");
                    hasil.append(autoEncryption(plainText, keyword));
                }
            }else if(cipherSelection.getSelectedItem().equals("Vigenere")){
                String key = generateKey(plainText, keyword);
                if (hasil.getText() == "") {
                    cipherText(plainText, key);
                }else{
                    hasil.setText("");
                    hasil.append(cipherText(plainText, key));
                }
            }else if(cipherSelection.getSelectedItem().equals("Autokey-Vigenere")){
                String key = generateKey(plainText, keyword);
                if (hasil.getText() == "") {
                    String auto = autoEncryption(plainText, keyword);
                    hasil.append(cipherText(auto, key));
                }else{
                    hasil.setText("");
                    String auto = autoEncryption(plainText, keyword);
                    hasil.append(cipherText(auto, key));
                }
            }else if(cipherSelection.getSelectedItem().equals("Vigenere-Autokey")){
                String key = generateKey(plainText, keyword);
                if (hasil.getText() == "") {
                    String vig = cipherText(plainText, key);
                    hasil.append(autoEncryption(vig, keyword));
                }else{
                    hasil.setText("");
                    String vig = cipherText(plainText, key);
                    hasil.append(autoEncryption(vig, keyword));
                }  
            }
            
        }else if(e.getSource() == dekrip){
            if (cipherSelection.getSelectedItem().equals("Autokey")) {
                out = hasil.getText();
                hasil.setText("");
                hasil.append(autoDecryption(out, keyword));
            }else if(cipherSelection.getSelectedItem().equals("Vigenere")){
                String key = generateKey(plainText, keyword);
                out = hasil.getText();
                hasil.setText("");
                hasil.append(originalText(out, key));
            }else if(cipherSelection.getSelectedItem().equals("Autokey-Vigenere")){
                String key = generateKey(plainText, keyword);
                out = hasil.getText();
                String vig = originalText(out, key);
                hasil.setText("");
                hasil.append(autoDecryption(vig, keyword));
            }else if(cipherSelection.getSelectedItem().equals("Vigenere-Autokey")){
                String key = generateKey(plainText, keyword);
                out = hasil.getText();
                String auto = autoDecryption(out, keyword);
                hasil.setText("");
                hasil.append(originalText(auto, key));
            }
        }else{
            pText.setText("");
            key.setText("");
            hasil.setText("");
        }
        
    }

    // Java code to implement Vigenere Cipher



// This function generates the key in
// a cyclic manner until it's length isi'nt
// equal to the length of original text
    static String generateKey(String str, String key)
    {
        int x = str.length();

        for (int i = 0; ; i++)
        {
            if (x == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key+=(key.charAt(i));
        }
        return key;
    }

// This function returns the encrypted text
// generated with the help of the key
    static String cipherText(String str, String key)
    {
        String cipher_text="";

        for (int i = 0; i < str.length(); i++)
        {
            // converting in range 0-25
            int x = (str.charAt(i) + key.charAt(i)) %26;

            // convert into alphabets(ASCII)
            x += 'A';

            cipher_text+=(char)(x);
        }
        return cipher_text;
    }

// This function decrypts the encrypted text
// and returns the original text
    static String originalText(String cipher_text, String key)
    {
        String orig_text="";

        for (int i = 0 ; i < cipher_text.length() &&
                                i < key.length(); i++)
        {
            // converting in range 0-25
            int x = (cipher_text.charAt(i) -
                        key.charAt(i) + 26) %26;

            // convert into alphabets(ASCII)
            x += 'A';
            orig_text+=(char)(x);
        }
        return orig_text;
    }

    // This function will convert the lower case character to Upper case
    static String LowerToUpper(String s)
    {
        StringBuffer str =new StringBuffer(s);
        for(int i = 0; i < s.length(); i++)
        {
            if(Character.isLowerCase(s.charAt(i)))
            {
                str.setCharAt(i, Character.toUpperCase(s.charAt(i)));
            }
        }
        s = str.toString();
        return s;
    }

    //AutoKey

    public static String autoEncryption(String msg, String key)
    {
        int len = msg.length();
 
        // generating the keystream
        String newKey = key.concat(msg);
        newKey = newKey.substring(0, newKey.length() - key.length());
        String encryptMsg = "";
 
        // applying encryption algorithm
        for (int x = 0; x < len; x++) {
            int first = alphabet.indexOf(msg.charAt(x));
            int second = alphabet.indexOf(newKey.charAt(x));
            int total = (first + second) % 26;
            encryptMsg += alphabet.charAt(total);
        }
        return encryptMsg;
    }
 
    public static String autoDecryption(String msg, String key)
    {
        String currentKey = key;
        String decryptMsg = "";
 
        // applying decryption algorithm
        for (int x = 0; x < msg.length(); x++) {
            int get1 = alphabet.indexOf(msg.charAt(x));
            int get2 = alphabet.indexOf(currentKey.charAt(x));
            int total = (get1 - get2) % 26;
            total = (total < 0) ? total + 26 : total;
            decryptMsg += alphabet.charAt(total);
            currentKey += alphabet.charAt(total);
        }
        return decryptMsg;
    }

}
// This code has been contributed by 29AjayKumar