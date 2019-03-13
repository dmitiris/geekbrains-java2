import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingChat {
    public static void main(String[] args) {
        new MyWindow();
    }

}

class MyWindow extends JFrame {
    public MyWindow() {
        setTitle("Hello World");
        setBounds(800, 400, 400, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createChatWindow();
        setVisible(true);
    }

    private void createChatWindow(){
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new BorderLayout());
        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        centerPanel.add(jScrollPane, BorderLayout.CENTER);


        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        JTextField jTextField = new JTextField();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 15;
        bottomPanel.add(jTextField, gridBagConstraints);

        JButton jButton = new JButton("Send");
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 10;

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.append(jTextField.getText() + "\n");
                jTextField.setText("");
                jTextField.grabFocus();
            }
        });
        bottomPanel.add(jButton, gridBagConstraints);

        rootPane.setDefaultButton(jButton);
    }
}
