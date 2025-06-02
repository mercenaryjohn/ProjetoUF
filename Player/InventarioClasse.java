
package Player;

import Itens.*;

public class InventarioClasse
{
    // Insere um novo item no inventário, se houver espaço.
    public void adicionarItem (EscolherClasse classeEscolhida, Item item) 
    {
        classeEscolhida.getInventário().add(item);
        return;
    }

    // Retira um item do inventário.
    public void removerItem (EscolherClasse classeEscolhida, Item item)
    {
        classeEscolhida.getInventário().remove(item);
        return;
    }

    // Ativa o efeito do item no personagem.
    public void usarItem (EscolherClasse classeEscolhida, Item item)
    {
        item.usar(classeEscolhida);
    }
}
