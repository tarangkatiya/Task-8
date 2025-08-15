import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class Question {
    String questionText;
    String[] options;
    int correctOption;

    Question(String questionText, String[] options, int correctOption) {
        this.questionText = questionText;
        this.options = options;
        this.correctOption = correctOption;
    }
}

public class QuizGUI extends JFrame implements ActionListener {
    List<Question> questions;
    int currentIndex = 0;
    int score = 0;

    JLabel questionLabel;
    JRadioButton[] optionButtons = new JRadioButton[4];
    ButtonGroup optionsGroup;
    JButton nextButton;

    QuizGUI() {
        setTitle("Online Quiz App");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Question list
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?",
                new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 3));
        questions.add(new Question("Which language is used for Android development?",
                new String[]{"Java", "Swift", "C#", "PHP"}, 1));
        questions.add(new Question("Who is known as the father of computers?",
                new String[]{"Charles Babbage", "Alan Turing", "Bill Gates", "Steve Jobs"}, 1));

        // Question label
        questionLabel = new JLabel("Question");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel, BorderLayout.NORTH);

        // Options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1));
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        // Next button
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        add(nextButton, BorderLayout.SOUTH);

        loadQuestion();

        setVisible(true);
    }

    void loadQuestion() {
        if (currentIndex < questions.size()) {
            Question q = questions.get(currentIndex);
            questionLabel.setText("Q" + (currentIndex + 1) + ": " + q.questionText);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(q.options[i]);
            }
            optionsGroup.clearSelection();
        } else {
            JOptionPane.showMessageDialog(this, "Quiz Finished!\nYour Score: " + score + "/" + questions.size());
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentIndex < questions.size()) {
            int selected = -1;
            for (int i = 0; i < 4; i++) {
                if (optionButtons[i].isSelected()) {
                    selected = i + 1;
                }
            }
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Please select an option!");
                return;
            }
            if (selected == questions.get(currentIndex).correctOption) {
                score++;
            }
            currentIndex++;
            loadQuestion();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizGUI::new);
    }
}
