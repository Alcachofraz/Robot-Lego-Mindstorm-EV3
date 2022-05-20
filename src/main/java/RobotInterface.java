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
        JPanel headerPanel = new JPanel(new GridLayout(4, 4));

        JTextField robotName = new JTextField("");
        JTextField radius = new JTextField("0");
        JTextField angle = new JTextField("0");
        JTextField distance = new JTextField("0");
        JTextField xf = new JTextField("0");
        JTextField yf = new JTextField("0");
        JTextField of = new JTextField("0");
        JTextField wheelDistance = new JTextField("9.6");
        JCheckBox on = new JCheckBox();
        JButton trajectory = new JButton("Run trajectory");
        JButton wallChaser = new JButton("Run wall chaser");

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
        wheelDistance.getDocument().addDocumentListener(new DocumentListener() {
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
                try {
                    Variables.setWheelDistance(Double.parseDouble(wheelDistance.getText()));
                }
                catch (Exception e) {
                    Variables.setWheelDistance(9.6);
                }
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
                try {
                    Variables.setRadius(Double.parseDouble(radius.getText()));
                }
                catch (Exception e) {
                    Variables.setRadius(0);
                }
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
                try {
                    Variables.setAngle(Double.parseDouble(angle.getText()));
                }
                catch (Exception e) {
                    Variables.setAngle(0);
                }
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
                try {
                    Variables.setDistance(Double.parseDouble(distance.getText()));
                }
                catch (Exception e) {
                    Variables.setDistance(0);
                }
            }
        });
        xf.getDocument().addDocumentListener(new DocumentListener() {
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
                try {
                    Variables.setXf(Double.parseDouble(xf.getText()));
                }
                catch (Exception e) {
                    Variables.setXf(0);
                }
            }
        });
        yf.getDocument().addDocumentListener(new DocumentListener() {
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
                try {
                    Variables.setYf(Double.parseDouble(yf.getText()));
                }
                catch (Exception e) {
                    Variables.setYf(0);
                }
            }
        });
        of.getDocument().addDocumentListener(new DocumentListener() {
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
                try {
                    Variables.setOf(Double.parseDouble(of.getText()));
                }
                catch (Exception e) {
                    Variables.setOf(0);
                }
            }
        });
        trajectory.addActionListener((e) -> TrajectoryManager.run((int) Variables.getXf(), (int) Variables.getYf(), (int) Variables.getOf()));
        wallChaser.addActionListener((e) -> TrajectoryManager.runWallChaser());

        on.setText("On/Off");
        on.addItemListener((l) -> {
            if (l.getStateChange() == ItemEvent.SELECTED) {
                Variables.setOn(true);
                if (!Variables.getRobot().open(Variables.getRobotName())) {
                    Variables.setOn(false);
                    //on.setEnabled(false);
                }
                else {
                    Variables.getRobot().stop(true);
                    //on.setEnabled(true);
                }
            }
            else {
                //on.setEnabled(false);
                Variables.getRobot().close();
                Variables.setOn(false);
            }
        });

        headerPanel.add(buildLabelledTextField("Robot Name:", robotName));
        headerPanel.add(on);
        headerPanel.add(buildLabelledTextField("Wheel Distance:", wheelDistance));
        headerPanel.add(new JLabel(""));

        headerPanel.add(buildLabelledTextField("Radius:", radius));
        headerPanel.add(buildLabelledTextField("Angle:", angle));
        headerPanel.add(buildLabelledTextField("Distance:", distance));
        headerPanel.add(new JLabel(""));

        headerPanel.add(buildLabelledTextField("Xf:", xf));
        headerPanel.add(buildLabelledTextField("Yf:", yf));
        headerPanel.add(buildLabelledTextField("Of:", of));
        headerPanel.add(trajectory);

        headerPanel.add(wallChaser);
        headerPanel.add(new JLabel(""));
        headerPanel.add(new JLabel(""));
        headerPanel.add(new JLabel(""));

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
            Variables.getRobot().forward(Variables.getDistance());
            Variables.getRobot().stop(false);
        });
        backButton.setText("Backward");
        backButton.addActionListener((e) -> {
            Variables.getRobot().forward(-Variables.getDistance());
            Variables.getRobot().stop(false);
        });
        rightButton.setText("Right");
        rightButton.addActionListener((e) -> {
            Variables.getRobot().turnRight(Variables.getRadius(), Variables.getAngle());
            Variables.getRobot().stop(false);
        });
        leftButton.setText("Left");
        leftButton.addActionListener((e) -> {
            Variables.getRobot().turnLeft(Variables.getRadius(), Variables.getAngle());
            Variables.getRobot().stop(false);
        });
        stopButton.setText("Stop");
        stopButton.addActionListener((e) -> {
            Variables.getRobot().stop(true);
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
