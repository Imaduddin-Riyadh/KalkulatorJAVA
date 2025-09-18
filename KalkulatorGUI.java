import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KalkulatorGUI extends JFrame {

    private JTextField display;
    private JTextArea historyArea;
    private StringBuilder input = new StringBuilder();
    private ArrayList<String> history = new ArrayList<>();

    public KalkulatorGUI() {
        setTitle("Kalkulator Sederhana");
        setSize(350, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Layar utama
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 20));
        add(display, BorderLayout.NORTH);

        // Area history
        historyArea = new JTextArea(5, 20);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Panel tombol
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    // Metode matdas
    private double matdas(double a, double b, String operator) {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": 
                if (b == 0) {
                    JOptionPane.showMessageDialog(this, "Error: Pembagian dengan nol!");
                    return 0;
                }
                return a / b;
            default: return 0;
        }
    }

    // Listener tombol
    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.matches("[0-9]")) {
                input.append(command);
                display.setText(input.toString());
            } else if (command.matches("[+\\-*/]")) {
                // Cegah operator berulang tanpa angka
                if (input.length() > 0 && !input.toString().endsWith(" ")) {
                    input.append(" ").append(command).append(" ");
                    display.setText(input.toString());
                }
            } else if (command.equals("=")) {
                try {
                    String[] parts = input.toString().trim().split(" ");
                    if (parts.length == 3) {
                        double num1 = Double.parseDouble(parts[0]);
                        String operator = parts[1];
                        double num2 = Double.parseDouble(parts[2]);

                        double result = matdas(num1, num2, operator);
                        display.setText(String.valueOf(result));

                        // Simpan ke history
                        String record = input + " = " + result;
                        history.add(record);
                        historyArea.append(record + "\n");
                        input.setLength(0);
                    } else {
                        display.setText("Format salah!");
                    }
                } catch (Exception ex) {
                    display.setText("Error");
                }
            } else if (command.equals("C")) {
                input.setLength(0);
                display.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KalkulatorGUI().setVisible(true);
        });
    }
}
