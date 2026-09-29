import java.util.Locale;

public abstract class Product implements Vendavel {

    private String nome;
    private double preco;
    private int quantidade;

    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        if (preco < 0) {
            throw new QuantidadeInvalidaException(
                    "Preço inválido para o produto '" + nome + "': " + preco);
        }
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException(
                    "Quantidade inválida para o produto '" + nome + "': " + quantidade);
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Cada subclasse decide como calcular o valor total.
    public abstract double calcularValorTotal();

    public String getDescricao() {
        return String.format(new Locale("pt", "BR"),
                "%s | Preço: R$ %.2f | Quantidade: %d", nome, preco, quantidade);
    }

    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        if (quantidadeDesejada <= 0) {
            throw new ProdutoIndisponivelException(
                    "Quantidade desejada deve ser positiva (recebido: " + quantidadeDesejada + ").");
        }
        if (quantidadeDesejada > quantidade) {
            throw new ProdutoIndisponivelException(
                    "Estoque insuficiente de '" + nome + "': desejado " + quantidadeDesejada
                            + ", disponível " + quantidade + ".");
        }
        quantidade -= quantidadeDesejada;
    }

    // Sobrecarga (polimorfismo estático)
    public void aplicarDesconto(double percentual) {
        preco = preco - (preco * percentual / 100.0);
    }

    // O desconto aplicado nunca passa de descontoMaximo.
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        double percentualAplicado = Math.min(percentual, descontoMaximo);
        aplicarDesconto(percentualAplicado);
    }
}
