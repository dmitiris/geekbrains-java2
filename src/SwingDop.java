import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingDop {
    public static void main(String[] args) {
        new DataShowWindow();
    }
}

class DataShowWindow extends JFrame {
    JTextArea jTextArea = new JTextArea();

    public DataShowWindow() {
        setTitle("Show Data");
        setBounds(600, 200, 400, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createChatWindow();
        setVisible(true);
    }

    public void createChatWindow() {
        setLayout(new BorderLayout());

        jTextArea.setEditable(false);
        add(jTextArea, BorderLayout.CENTER);

        JButton jButton = new JButton("Add");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DataInputWindow(DataShowWindow.this);
            }
        });
        add(jButton, BorderLayout.SOUTH);
        rootPane.setDefaultButton(jButton);
    }

    public void setData(String[] personData){
        jTextArea.setText("");
        for (int i = 0; i < personData.length; i++){
            jTextArea.append(personData[i] + "\n");
        }
    }
}

class DataInputWindow extends JFrame {
    DataShowWindow dataWin;

    public DataInputWindow(DataShowWindow dataWin) {
        this.dataWin = dataWin;
        setTitle("Input Data");
        setBounds(800, 400, 400, 200);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createInputWindow();
        setVisible(true);
    }

    private void createInputWindow() {
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel();

        centerPanel.setLayout(new GridLayout(3, 1));
        JTextField jTextField1 = new JTextField();
        jTextField1.setPreferredSize(new Dimension(300,28));
        centerPanel.add(jTextField1);

        JTextField jTextField2 = new JTextField();
        jTextField2.setPreferredSize(new Dimension(300,28));
        centerPanel.add(jTextField2);

        JTextField jTextField3 = new JTextField();
        jTextField3.setPreferredSize(new Dimension(300,28));
        centerPanel.add(jTextField3);

        add(centerPanel, BorderLayout.CENTER);

        JButton jButton = new JButton("Save");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] personData = {
                        jTextField1.getText(),
                        jTextField2.getText(),
                        jTextField3.getText()
                };
                dataWin.setData(personData);
                dispose();
            }
        });

        add(jButton, BorderLayout.SOUTH);
        rootPane.setDefaultButton(jButton);
    }
}
