package view;

import controller.Controller;
import javax.swing.*;
import java.awt.event.*;

public class MainMenuView extends JFrame {
	
    public MainMenuView(Controller controller) {
        setTitle("Clínica Buena Piel - Menú Principal");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton pacienteBtn = new JButton("Paciente");
        pacienteBtn.setBounds(50, 40, 100, 40);
        JButton medicoBtn = new JButton("Médico");
        medicoBtn.setBounds(180, 40, 100, 40);
        JButton recepBtn = new JButton("Recepcionista");
        recepBtn.setBounds(100, 100, 130, 40);

        add(pacienteBtn);
        add(medicoBtn);
        add(recepBtn);

        pacienteBtn.addActionListener(e -> {
            new PacienteView(controller);
            dispose();
        });
        medicoBtn.addActionListener(e -> {
            new MedicoView(controller);
            dispose();
        });
        recepBtn.addActionListener(e -> {
            new RecepcionistaView(controller);
            dispose();
        });

        setVisible(true);
    }
}