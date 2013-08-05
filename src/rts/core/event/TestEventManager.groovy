package rts.core.event

class GameScript{
    def player = new Player(name:"Kira")
    def em
    def startGame(){
        def et1= [type:"Game Start",data:1];
        em.addManagedEvent(et1)
        em.postEvent([type:"Game Start",data:1])
        em.processEvents()
    }
}

class Player{
    def name
    def count = 0
}
myGame=new GameScript()
em = new ExEventManager(myGame)
myGame.em = em

def tg1 = new ExTrigger();
tg1.events << [type:"Game Start",data:1];

// CONDITIONS
tg1.conditions << { 
    game.player.count++
    return game.player.count % 2 == 1}

tg1.actions<<"""
    game.player.name = "Diamond"
    println "From a String : "+game.player.name
"""

// ACTIONS
tg1.actions << {println "Action 1 " + game.player.name}

em.addTrigger(tg1)
em.scanMappingAll()

myGame.startGame()

//println em.equiv(et1, [type:"Game Start",data:1])

def tengine = new groovy.text.GStringTemplateEngine() 
def strTemp1= '($unitName $insOp $structName) $operation $value'
def template = tengine.createTemplate strTemp1
def values = [:]

values["unitName"] = "Triggering unit"
values["insOp"] = "is"
values["structName"] = "A structure"
values["operation"] = "Equal to"
values["value"] = "True"

println template.make(values)

resultString = """
((Triggering unit) is (A structure)) Equal to True
"""


