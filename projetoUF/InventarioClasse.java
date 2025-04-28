import java.util.List;

public class InventarioClasse
{
    // Insere um novo item no inventário, se houver espaço.
    public void adicionarItem (EscolherClasse classeEscolhida, Item item) 
    {
        List<Item> inventárioNovo = classeEscolhida.getInventário();
        inventárioNovo.add(item);
        //System.out.println(item.getClass()); //DEBUG
        classeEscolhida.setInventário(inventárioNovo);
        return;
    }

    // Retira um item do inventário.
    public void removerItem (EscolherClasse classeEscolhida, Item item)
    {
        List<Item> inventárioNovo = classeEscolhida.getInventário();
        inventárioNovo.remove(item);
        classeEscolhida.setInventário(inventárioNovo);
        return;
    }

    // Ativa o efeito do item no personagem.
    public void usarItem (EscolherClasse classeEscolhida, Item item)
    {
        item.usar(classeEscolhida);
    }
}
