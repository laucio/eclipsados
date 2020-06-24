package traductor_de_comandos;

import java.util.ArrayList;

import juego_de_aventura.*;

public class TranslatorVerbTalkToNPC implements CommandTranslator {
	
	private final static String[] VERBO_HABLAR = { " hablar ", " conversar ", " dialogar ",
			" charlar ", " cotorrear ", " platicar ", " chusmear "};
	
	private CommandTranslator next = null;

	public TranslatorVerbTalkToNPC(CommandTranslator next){
		this.next = next;
	}
	
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
boolean translated = false;
		
		int i = 0;
		while(!translated && i<VERBO_HABLAR.length) {
			
			if(command.contains(VERBO_HABLAR[i])) {
				translated = true;
				action.setAction("hablar");
			}
			
		i++;
		}
		
		if(translated) {
			Player player = game.getPlayer();
			Location location = player.getCurrentLocation();
			ArrayList<String> npcs = location.getNPCS();
			
			i=0;
			boolean found = false;
			while(!found && npcs != null && i < npcs.size()) {
				if(command.contains(npcs.get(i))) {
					found = true;
					action.setTarget(npcs.get(i));
					action.setCondition("npcs");
				}
				
			i++;
			}
			
			/*
			 * if(!found){
			 * action.setTarget("unknown");
			 * action.setCondition("unknown");
			 * 
			 * }
			 */
			
		}
		
		if(!translated) {
			next.translateCommand(command, action, game);
		}
		
		

	}

}
