import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;

public class RobotInterface extends JFrame {

    RobotInterface() {
        super("Robot Interface");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                //Variables.getRobot().CloseEV3();
                Variables.getRobot().close();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(0, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        mainPanel.add(buildHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(buildControllerPanel(), BorderLayout.CENTER);
        mainPanel.add(buildConsolePanel(8), BorderLayout.SOUTH);
        this.setContentPane(mainPanel);
        this.pack();
    }

    JPanel buildHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridLayout(2, 3));

        JTextField robotName = new JTextField("");
        JTextField radius = new JTextField("0");
        JTextField angle = new JTextField("0");
        JTextField distance = new JTextField("0");
        JCheckBox on = new JCheckBox();

        robotName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                general();
            }
            public void removeUpdate(DocumentEvent e) {
                general();
            }
            public void insertUpdate(DocumentEvent e) {
                general();
            }
            public void general() {
                Variables.setRobotName(robotName.getText());
            }
        });
        radius.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                general();
            }
            public void removeUpdate(DocumentEvent e) {
                general();
            }
            public void insertUpdate(DocumentEvent e) {
                general();
            }
            public void general() {
                Variables.setRadius(Integer.parseInt(radius.getText().isEmpty() ? "0" : radius.getText()));
            }
        });
        angle.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                general();
            }
            public void removeUpdate(DocumentEvent e) {
                general();
            }
            public void insertUpdate(DocumentEvent e) {
                general();
            }
            public void general() {
                Variables.setAngle(Integer.parseInt(angle.getText().isEmpty() ? "0" : angle.getText()));
            }
        });
        distance.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                general();
            }
            public void removeUpdate(DocumentEvent e) {
                general();
            }
            public void insertUpdate(DocumentEvent e) {
                general();
            }
            public void general() {
                Variables.setDistance(Integer.parseInt(distance.getText().isEmpty() ? "0" : distance.getText()));
            }
        });

        on.setText("On/Off");
        on.addItemListener((l) -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                Variables.setOn(true);
                if (/*!Variables.getRobot().OpenEV3(Variables.getRobotName())*/!Variables.getRobot().open(Variables.getRobotName())) {
                    Variables.setOn(false);
                }
            }
            else {
                //Variables.getRobot().CloseEV3();
                Variables.getRobot().close();
                Variables.setOn(false);
            }
        });

        headerPanel.add(buildLabelledTextField("Robot Name:", robotName));
        headerPanel.add(on);
        headerPanel.add(new JLabel(""));
        headerPanel.add(buildLabelledTextField("Radius:", radius));
        headerPanel.add(buildLabelledTextField("Angle:", angle));
        headerPanel.add(buildLabelledTextField("Distance:", distance));

        return headerPanel;
    }

    JPanel buildLabelledTextField(String label, JTextField textField) {
        JPanel labelledTextField = new JPanel(new GridLayout(1, 2));
        labelledTextField.add(new JLabel(label));
        labelledTextField.add(textField);
        return labelledTextField;
    }

    JPanel buildConsolePanel(int lines) {
        JPanel consolePanel = new JPanel(new BorderLayout(5,5));
        JTextPane consoleArea = new JTextPane();

        consolePanel.add(new JScrollPane(consoleArea));
        MessageConsole messageConsole = new MessageConsole(consoleArea, true);
        messageConsole.redirectOut(Color.BLACK, System.out);
        messageConsole.redirectErr(Color.RED, System.err);
        messageConsole.setMessageLines(lines);

        return consolePanel;
    }

    JPanel buildControllerPanel() {
        JPanel controllerPanel = new JPanel(new GridLayout(3, 3));

        JButton frontButton = new JButton();
        JButton backButton = new JButton();
        JButton rightButton = new JButton();
        JButton leftButton = new JButton();
        JButton stopButton = new JButton();

        frontButton.setText("Forward");
        frontButton.addActionListener((e) -> {
            //Variables.getRobot().Reta(Variables.getDistance());
            //Variables.getRobot().Parar(false);
            Variables.getRobot().forward(Variables.getDistance());
        });
        backButton.setText("Backward");
        backButton.addActionListener((e) -> {
            //Variables.getRobot().Reta(-Variables.getDistance());
            //Variables.getRobot().Parar(false);
            Variables.getRobot().forward(-Variables.getDistance());
        });
        rightButton.setText("Right");
        rightButton.addActionListener((e) -> {
            //Variables.getRobot().CurvarDireita(Variables.getRadius(), Variables.getAngle());
            //Variables.getRobot().Parar(false);
            Variables.getRobot().turnRight(Variables.getRadius(), Variables.getAngle());
        });
        leftButton.setText("Left");
        leftButton.addActionListener((e) -> {
            //Variables.getRobot().CurvarEsquerda(Variables.getRadius(), Variables.getAngle());
            //Variables.getRobot().Parar(false);
            Variables.getRobot().turnLeft(Variables.getRadius(), Variables.getAngle());
        });
        stopButton.setText("Stop");
        stopButton.addActionListener((e) -> {
            //Variables.getRobot().Parar(true);
            Variables.getRobot().stopNow();
        });
        controllerPanel.add(new JLabel(""));
        controllerPanel.add(frontButton);
        controllerPanel.add(new JLabel(""));
        controllerPanel.add(leftButton);
        controllerPanel.add(stopButton);
        controllerPanel.add(rightButton);
        controllerPanel.add(new JLabel(""));
        controllerPanel.add(backButton);
        controllerPanel.add(new JLabel(""));
        return controllerPanel;
    }
}
