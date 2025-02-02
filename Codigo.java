package Controle_Estoque;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

class Produto {
    private String nome;
    private int quantidade;
    private double preco;

    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }
    public double getPreco() { return preco; }

    public void adicionarEstoque(int qtd) {
        this.quantidade += qtd;
    }

    public boolean retirarEstoque(int qtd) {
        if (qtd <= quantidade) {
            this.quantidade -= qtd;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return nome + ", Quantidade: " + quantidade + ", Preço: R$ " + preco;
    }
}

class ControleEstoque {
    private List<Produto> produtos = new ArrayList<>();

    public ControleEstoque() {
    	produtos.add(new Produto("Placa Mãe", 200, 799.45));
        produtos.add(new Produto("Memória RAM", 200, 250.50));
        produtos.add(new Produto("Cooler", 200, 79.99));
        produtos.add(new Produto("Pen Drive", 200, 8.99));
        produtos.add(new Produto("CD", 200, 2.75));
        produtos.add(new Produto("Notebook", 100, 3499.99));
        produtos.add(new Produto("HD", 150, 450.00));
        produtos.add(new Produto("Fonte", 180, 249.99));
        produtos.add(new Produto("Processador", 120, 1099.00));
        produtos.add(new Produto("SSD", 170, 129.99));
        produtos.add(new Produto("Fone de ouvido", 200, 39.50));
        produtos.add(new Produto("Kit gamer", 90, 849.00));
        produtos.add(new Produto("Gabinete", 110, 259.99));
        produtos.add(new Produto("Mouse", 250, 49.99));
        produtos.add(new Produto("Teclado", 240, 99.99));
        produtos.add(new Produto("WebCam", 130, 199.99));
        produtos.add(new Produto("Adaptador USB", 180, 49.99));
        produtos.add(new Produto("Modem", 75, 199.99));
        produtos.add(new Produto("Repetidor", 90, 79.99));
        produtos.add(new Produto("Placas de rede", 140, 59.99));
    }

    public Produto buscarProduto(String nome) {
        for (Produto p : produtos) {
            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}

public class Main {
    public static void main(String[] args) {
        ControleEstoque estoque = new ControleEstoque();

        JFrame frame = new JFrame("Controle de Estoque");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.RED, getWidth(), getHeight(), Color.MAGENTA);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridLayout(8, 2));
        frame.setContentPane(panel);

        JLabel nomeLabel = new JLabel("Nome do Produto:");
        JTextField nomeField = new JTextField();
        JLabel quantidadeLabel = new JLabel("Quantidade:");
        JTextField quantidadeField = new JTextField();
        JButton venderButton = new JButton("Vender Produto");
        JButton adicionarButton = new JButton("Adicionar Estoque");
        JButton gerarRelatorioButton = new JButton("Gerar Relatório");
        JLabel statusLabel = new JLabel();
        JLabel detalhesProdutoLabel = new JLabel();

        venderButton.addActionListener(e -> {
            String nome = nomeField.getText();
            int quantidade;
            try {
                quantidade = Integer.parseInt(quantidadeField.getText());
            } catch (NumberFormatException ex) {
                statusLabel.setText("Quantidade inválida");
                return;
            }
            Produto produto = estoque.buscarProduto(nome);
            if (produto != null) {
                if (produto.retirarEstoque(quantidade)) {
                    double total = produto.getPreco() * quantidade;
                    statusLabel.setText("Venda realizada com sucesso. Total: R$ " + total);
                    detalhesProdutoLabel.setText("Preço: R$ " + produto.getPreco() + " | Estoque disponível: " + produto.getQuantidade());
                } else {
                    statusLabel.setText("Estoque insuficiente");
                }
            } else {
                statusLabel.setText("Produto não encontrado");
            }
        });

        adicionarButton.addActionListener(e -> {
            String nome = nomeField.getText();
            int quantidade;
            try {
                quantidade = Integer.parseInt(quantidadeField.getText());
            } catch (NumberFormatException ex) {
                statusLabel.setText("Quantidade inválida");
                return;
            }
            Produto produto = estoque.buscarProduto(nome);
            if (produto != null) {
                produto.adicionarEstoque(quantidade);
                statusLabel.setText("Estoque adicionado com sucesso");
            } else {
                statusLabel.setText("Produto não encontrado");
            }
        });

        gerarRelatorioButton.addActionListener(e -> {
            try (FileWriter writer = new FileWriter("relatorio_estoque.txt")) {
                for (Produto p : estoque.getProdutos()) {
                    writer.write(p.toString() + "\n");
                }
                statusLabel.setText("Relatório gerado com sucesso!");
            } catch (IOException ex) {
                statusLabel.setText("Erro ao gerar relatório");
            }
        });

        panel.add(nomeLabel);
        panel.add(nomeField);
        panel.add(quantidadeLabel);
        panel.add(quantidadeField);
        panel.add(venderButton);
        panel.add(adicionarButton);
        panel.add(gerarRelatorioButton);
        panel.add(statusLabel);
        panel.add(detalhesProdutoLabel);
        frame.setVisible(true);
    }
}
