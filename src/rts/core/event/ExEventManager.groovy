package rts.core.event

import rts.gameplay.actions.RTSAction
/**
 *
 * @author cuong.nguyenmanh2
 */
class ExEventManager {
    def game
    def eventQueue = []
    def triggers = [];
    def managedEvents = []
    def mapping = [:]
    def eventInstanceForce = false
    def shell
    def binding

    ExEventManager(game){
        this.game = game
        this.binding = new Binding()
        this.shell = new GroovyShell( binding )
        binding.setVariable("game",game)
    }
    
    def init(){
        //fill managed event
    }
    
    def postEvent(event){
        eventQueue << event;
    }
    
    def performActions(actions){
        actions.each{a->
            //println a
            if (a instanceof Closure){
                a.delegate = this
                a()
            } else if (a instanceof String){
                shell.evaluate(a)
            } else if (a instanceof RTSAction){
                a.doAction()
            }
        }
    }

    def lookupSubcribers(event){
        def lookupResult = []
        def eventKey
        if (eventInstanceForce){
            eventKey = event
        } else {
            if (event instanceof Map){
                eventKey = managedEvents.find{ equivMap(event,it) == true}
            }
        }
        if (eventKey != null){
            if (mapping[eventKey]!=null){
                lookupResult.addAll(mapping[eventKey])
            }
        }
        return lookupResult
    }
    
    def addTrigger(trigger){
        triggers<<trigger
    }
    def addManagedEvent(e){
        if (!managedEvents.contains(e)){
            managedEvents << e
        }
    }
    def scanMapping(trigger){
        trigger.events.each {e->
            addManagedEvent(e)
            if (mapping[e]==null){
                mapping[e]=[]
            }
            mapping[e]<<trigger
            
        }
    }
    def scanMappingAll(){
            
        triggers.each{trigger->
            trigger.events.each {e->
                addManagedEvent(e)
                if (mapping[e]==null){
                    mapping[e]=[]
                }
                mapping[e]<<trigger
            
            }
        }
    }
    def processEvents(){
        while(!eventQueue.isEmpty()){
            def e = eventQueue.pop()
            def listSubs= lookupSubcribers(e);
            if (listSubs.isEmpty()){
                return;
            }
            listSubs.each{trigger->
                println trigger
                if (passConditions(trigger.conditions)){
                    performActions(trigger.actions)
                }
            }
        }
    }
    def passConditions(conditions){
        for (c in conditions){
            if (c instanceof Closure){
                c.delegate = this
                if (c() == false){
                    return false;
                }
            } else if (c instanceof String){
                if (shell.evaluate(c)){
                    return false;
                }
            } else if (c instanceof Boolean){
                if (c){
                    return false;
                }
            }
        }
        return true
    }
    def doProcessEvent(){
        
    }
    def equivMap(a, b){
        if (a instanceof Map && b instanceof Map){
            return equiv((Map)a, (Map)b)
        } else {
            return false;
        }
    }
    def equiv(Map a, Map b) {
        a.size() == b.size() && a.every { k, v -> b.containsKey(k) && equiv(v, b[k]) }
    }

    def equiv(List a, List b) {
        a.toSet() == b.toSet()
    }

    def equiv(a, b) {
        a == b
    } 
}

