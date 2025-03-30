import java.util.List;

public class InventarioClasse
{
    // Insere um novo item no inventário, se houver espaço.
    public void adicionarItem (EscolherClasse classeEscolhida, String nomeItem) 
    {
        List<String> inventárioNovo = classeEscolhida.getInventário();
        inventárioNovo.add(nomeItem);
        classeEscolhida.setInventário(inventárioNovo);
        return;
    }

    // Retira um item do inventário.
    public void removerItem (EscolherClasse classeEscolhida, String nomeItem)
    {
        List<String> inventárioNovo = classeEscolhida.getInventário();
        inventárioNovo.remove(nomeItem);
        classeEscolhida.setInventário(inventárioNovo);
        return;
    }

    // Ativa o efeito do item no personagem.
    public void usarItem (EscolherClasse classeEscolhida, String nomeItem)
    {
        if (nomeItem.equals("Água"))
        {
            classeEscolhida.setSede(classeEscolhida.getSede() + 50); //TODO: número temporário
        }
    }
}
