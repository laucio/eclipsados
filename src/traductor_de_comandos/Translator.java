package traductor_de_comandos;

import juego_de_aventura.*;

public class Translator implements CommandTranslator{
	
	CommandTranslator translatorDefault;
	CommandTranslator verbToGoOut;
	CommandTranslator verbToGo;
	CommandTranslator verbToLeave;
	CommandTranslator verbToTake;
	CommandTranslator verbToObserve;
	CommandTranslator verbToTalk;
	CommandTranslator verbToUse;
	CommandTranslator verbToOpen;
	CommandTranslator verbToDrink;
	CommandTranslator verbToEat;
	CommandTranslator verbToAttack;
	CommandTranslator verbToWatchInventory;
	CommandTranslator verbToSeeAround;
	CommandTranslator translatorOthers;
	
	public Translator() {
		translatorDefault = new TranslatorDefault();
		verbToGoOut = new TranslatorGoOut(translatorDefault);
		verbToGo = new TranslatorWithVerbGo(verbToGoOut);
		verbToLeave = new TranslatorVerbLeave(verbToGo);
		verbToTake = new TranslatorWithVerbTake(verbToLeave);
		verbToObserve = new TranslatorVerbObserve(verbToTake);
		verbToUse = new TranslatorWithVerbUse(verbToObserve);
		verbToOpen = new TranslatorVerbOpen(verbToUse);
		verbToDrink = new TranslatorVerbDrink(verbToOpen);
		verbToEat = new TranslatorVerbEat(verbToDrink);
		verbToTalk = new TranslatorVerbTalkToNPC(verbToEat);
		verbToAttack = new TranslatorVerbAttack(verbToTalk);
		verbToWatchInventory = new TranslatorVerbWatchInventory(verbToAttack);
		verbToSeeAround = new TranslatorVerbSeeAround(verbToWatchInventory);
		translatorOthers = new TranslatorOthers(verbToSeeAround);
	}
	
	@Override
	public void translateCommand(String command, Action action, Game game) {
		
		command = " " + command.toLowerCase() + " ";
		
		translatorOthers.translateCommand(command, action, game);
	}
	
	
}
