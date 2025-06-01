public class EventoCriatura extends Evento
{
    public void executar(Personagem player, String eventoEscolhido, Combate objCombate)
    {
        if(eventoEscolhido.equals("Lobo"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(50);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(8);
            objCombate.setDanoSanidade(5);
            objCombate.setEmCombate(true);
        }
        else if(eventoEscolhido.equals("Urso"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(100);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(20);
            objCombate.setDanoSanidade(6);
            objCombate.setEmCombate(true);
        }
        else if(eventoEscolhido.equals("Explorador perdido"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(80);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(5);
            objCombate.setDanoSanidade(6);
            objCombate.setEmCombate(true);
        }
        else if(eventoEscolhido.equals("Criatura"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(50);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(6);
            objCombate.setDanoSanidade(12);
            objCombate.setEmCombate(true);
        }
        else if(eventoEscolhido.equals("Jacaré"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(100);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(15);
            objCombate.setDanoSanidade(4);
            objCombate.setEmCombate(true);
        }
        else if(eventoEscolhido.equals("Piranha"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(30);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(5);
            objCombate.setDanoSanidade(2);
            objCombate.setEmCombate(true);
        }
        else if(eventoEscolhido.equals("Sobrevivente hostil"))
        {
            objCombate.setNomeInimigo(eventoEscolhido);
            objCombate.setVidaInimigo(70);
            objCombate.setVidaMáximaInimigo(objCombate.getVidaInimigo());
            objCombate.setDanoInimigo(6);
            objCombate.setDanoSanidade(2);
            objCombate.setEmCombate(true);
        }
    }
}
