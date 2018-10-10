package ants;

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class PlayRoom extends Frame {
    private static final long serialVersionUID = 1L;
    protected static boolean[][] dirs = new boolean[32][5];
    protected static int[] pos = {3, 8, 11, 16, 25};
    private ControlJPanel controlJPanel = null;
    private PaintActiveAntsJPanel paintActiveAntsJPanel = null;
    private boolean isSuspend = false;// isSuspend是否暂停

    private static final String STR_1 = "Start";
    private static final String STR_2 = "Pause";

    private JLabel textTimeLabel = null;
    private JLabel textCountLabel = null;

    transient CreepGame creepGame = new CreepGame();
    protected int numberDirs;

    // 排列组合初始化方向
    public static void initDirections() {
        for (int i = 0; i < dirs.length; i++) {
            int sum = i;
            for (int j = dirs[i].length - 1; j >= 0; j--) {
                dirs[i][j] = sum % 2 != 0;
                sum /= 2;
            }
        }
    }

    public PlayRoom() {
        super("动态演示蚂蚁爬杆行为");
        this.setBounds(200, 100, 1000, 300);//设置窗体的长宽各为:900 200,让其显示在距屏幕左上方坐标(200,100)处
        this.setLayout(new GridLayout(2, 1));//网格2行1列
        // new 对象
        controlJPanel = new ControlJPanel();
        paintActiveAntsJPanel = new PaintActiveAntsJPanel();
        creepGame.situation(0);
        this.add(controlJPanel);
        this.add(paintActiveAntsJPanel);
        setVisible(true);
    }

    // 控制面板
    public class ControlJPanel extends JPanel implements ActionListener {
        private static final long serialVersionUID = 1L;
        protected JButton jButtonPause = null;
        protected JButton jButtonRestart = null;// 暂停/开始按钮
        protected JTextField textSituation = null;
        protected JLabel situation = null;
        protected JLabel displayDirs = null;

        public ControlJPanel() {
            this.setLayout(null);// 绝对定位
            // new 对象
            jButtonPause = new JButton(STR_1);// 暂停
            jButtonRestart = new JButton("Restart");// 暂停
            textSituation = new JTextField(20);
            JLabel label = new JLabel("请输入：");
            textTimeLabel = new JLabel();
            textCountLabel = new JLabel();
            situation = new JLabel();
            situation.setText("第--种情况: ");
            displayDirs = new JLabel();
            displayDirs.setText("5种蚂蚁的方向: " + " 1:   " + "--"
                    + "    2:   " + "--"
                    + "    3:   " + "--"
                    + "    4:   " + "--"
                    + "    5:   " + "--");

            // 设置位置
            jButtonPause.setBounds(100, 75, 90, 30);
            jButtonRestart.setBounds(200, 75, 80, 30);
            label.setBounds(100, 20, 75, 30);
            textSituation.setBounds(170, 20, 100, 30);
            textTimeLabel.setBounds(550, 20, 100, 30);
            textCountLabel.setBounds(650, 20, 100, 30);
            setJLabel();

            situation.setBounds(450, 20, 80, 30);
            displayDirs.setBounds(450, 65, 580, 30);
            // jButton_verify加监听
            jButtonPause.addActionListener(this);
            jButtonRestart.addActionListener(this);
            textSituation.addActionListener(this);
            // 加组件
            this.add(situation);
            this.add(displayDirs);
            this.add(jButtonPause);
            this.add(jButtonRestart);
            this.add(label);
            this.add(textSituation);
            this.add(textTimeLabel);
            this.add(textCountLabel);
        }

        public void setJLabel() {
            textTimeLabel.setText("time: " + (creepGame.time - 1) + "s");
            textCountLabel.setText("count: " + creepGame.count + "s");
        }

        public void updateDisplay() {
            situation.setText("第" + (numberDirs + 1) + "种情况: ");
            displayDirs.setText("5种蚂蚁的方向: " + " 1:   " + creepGame.ants[0].displayDirection()
                    + "    2:   " + creepGame.ants[1].displayDirection()
                    + "    3:   " + creepGame.ants[2].displayDirection()
                    + "    4:   " + creepGame.ants[3].displayDirection()
                    + "    5:   " + creepGame.ants[4].displayDirection());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(STR_2)) {
                jButtonPause.setText("Continue");
                isSuspend = true;
            }
            if (e.getActionCommand().equals(STR_1) || e.getActionCommand().equals("Continue")) {
                jButtonPause.setText(STR_2);
                isSuspend = false;
            }
            if (e.getActionCommand().equals("Restart")) {
                paintActiveAntsJPanel.repaint();
                paintActiveAntsJPanel.start();
                jButtonPause.setText(STR_2);
                isSuspend = false;
            }
            if (e.getSource() == textSituation) {
                try {
                    numberDirs = Integer.parseInt(textSituation.getText()) - 1;
                    if (numberDirs < 0 || numberDirs > 31) {
                        numberDirs = 0;
                    }
                    setDirs(numberDirs);
                    this.updateDisplay();
                    paintActiveAntsJPanel.start();
                    creepGame.situation(numberDirs);
                    jButtonPause.setText(STR_1);
                    textSituation.setText(null);
                } catch (NumberFormatException e1) {
                    Logger log = Logger.getLogger(e1.getMessage());
                    log.info(e1.getMessage());
                }
            }
        }
    }

    public void setDirs(int numberDirs) {
        for (int i = 0; i < creepGame.ants.length; i++) {
            creepGame.ants[i].setDirection(dirs[numberDirs][i]);
        }
    }

    // PaintActiveAntsJPanel 画活动的蚂蚁
    public class PaintActiveAntsJPanel extends JPanel implements ActionListener {
        private static final long serialVersionUID = 1L;
        private int width1 = 30;// 设置像素为1；一个像素等于10个int
        protected Timer timer = null;

        public PaintActiveAntsJPanel() {
            timer = new Timer(1000, this);
            start();
        }

        public void start() {
            creepGame.count = 0;// 重新开始
            isSuspend = true;
            timer.restart();
            timer.setDelay(1000);
        }

        public void end() {
            timer.stop();
            controlJPanel.jButtonPause.setText(STR_1);
        }

        @Override
        public void paint(Graphics g) {
            if (creepGame.count == creepGame.time) end();
            super.paint(g);// 擦掉原来的
            controlJPanel.updateDisplay();
            g.drawLine(40, 60, 940, 60);
            int k = 0;
            for (int j = 1; j <= 30; j++) {// 画30个位置
                while (k < 5 && creepGame.antsDirection[creepGame.count][k] == -2) k++;
                if (creepGame.pole[creepGame.count][j] != 0 && k < 5) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    // 从本地读取一张图片
                    Image image;
                    if (creepGame.antsDirection[creepGame.count][k] == 1) {
                        String filepath1 = "1.jpg";
                        image = Toolkit.getDefaultToolkit().getImage(filepath1);
                        creepGame.ants[k].direction = true;
                    } else {
                        String filepath1 = "2.jpg";
                        image = Toolkit.getDefaultToolkit().getImage(filepath1);
                        creepGame.ants[k].direction = false;
                    }
                    k++;
                    // 绘制图片（如果宽高传的不是图片原本的宽高, 则图片将会适当缩放绘制）
                    g2d.drawImage(image, 10 + j * width1, 30, width1, width1, this);
                }
            }
            controlJPanel.updateDisplay();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.repaint();
            controlJPanel.setJLabel();

            if (isSuspend) {// 如果暂停，那么记录当前位置
                return;
            }
            creepGame.count++;
            controlJPanel.setJLabel();
        }
    }

    public static void main(String[] args) {
        PlayRoom playRoom = new PlayRoom();
        PlayRoom.initDirections();
        playRoom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}