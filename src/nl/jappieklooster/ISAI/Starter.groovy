package nl.jappieklooster.ISAI


class Starter {

	static main(args) {
	
		World w = new World()
		while(true){
			Thread.sleep(1000)
			w.zelda.update(10)
		}
	}

}
