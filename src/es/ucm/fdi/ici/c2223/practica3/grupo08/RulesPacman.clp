;FACTS ASSERTED BY GAME INPUT


(deftemplate PACMAN
    (slot distToLair(type INTEGER))
    (slot distToNearestCorner(type INTEGER))
    (slot distToNearestChasingNotBehind(type INTEGER))
    (slot distToNearestChasingAnyDir(type INTEGER))
    (slot distToNearestEdible(type INTEGER))
    (slot distToNearestPP(type INTEGER))
    (slot distToNearestPill(type INTEGER))
)

;ACTION FACTS
(deftemplate ACTION
    (slot id) (slot info (default "")) (slot priority (type INTEGER)) 

)

;RULES

;2< chasing ghosts cerca

;1 si hay mas de dos fantasmas agresivos cerca y se puede llegar a PP ve a PP
(defrule PacmanGoPPMultipleGhosts
(PACMAN (nearChasingGhosts ?g)) (test (<= 2 ?g))
    (PACMAN (PacmanCanReachPP))    
    =>
    (assert (ACTION (id ...goPP) (info "2< Chasing ghost near Pacman && PP range -> Go PP") (priority 100)))
)

;se podria crear una nueva accion que sea comer en camino no haya fantasmas para ganar algo de tiempo
;2 si hay 3 fantasmas agresivos o mas y no se puede llegar a PP estas encerrado probablemente, come y muere
(defrule PacmanEatAll
 (PACMAN (nearChasingGhosts ?g)) (test (<= 3 ?g))
    (PACMAN (!PacmanCanReachPP))   
    =>
    (assert (ACTION (id ...eatNEarestP) (info " 3< Chasing ghost near Pacman && PP non range -> Eat") (priority 100)))
)

;habria que hacer que fuera del que no le persigue en misma direccion
;3 si hay mas de dos fantasmas agresivos cerca y no se puede llegar a PP huye
(defrule PacmanAwayGhosts
 (PACMAN (nearChasingGhosts ?g)) (test (== 2 ?g))
 (PACMAN (PacmanCanReachPP false))   
    =>
    (assert (ACTION (id ...AwayNearestGhost) (info "2 Chasing ghost near Pacman && PP non range -> Away") (priority 100)))
)

; a partir de aqui <=1 fantasmas agresivos, para este caso falta meter el casod e que te persiga uno pero se pueda comer alguno

;4 chasing ghost cerca no persigue y se puede llegar a pp
(defrule PacmanGoPPSingleGhost
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman false)) 
    (PACMAN (PacmanCanReachPP true))    
    =>
    (assert 
        (ACTION (id ...GoPP) (info "1 Chasing ghost near Pacman && non follow && PP range -> Go PP") (priority 90))
    )
)

;5 chasing ghost cerca no persigue y no se puede llegar a pp
(defrule PacmanAwaySingleGhost
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman false)) 
    (PACMAN (PacmanCanReachPP false))    
    =>
    (assert 
        (ACTION (id ...AwayNearestGhost) (info "1 Chasing ghost near Pacman && non follow && PP non range -> Away") (priority 90))
    )
)

;no se si es misma direccion sigue comiendo
;6 chasing ghost cerca persigue, es interseccion, cercaPP
(defrule PacmanDodgePP
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman true)) 
     (PACMAN (isInIntersection true)) 
       (PACMAN (PPNear true)) 
    =>
    (assert 
        (ACTION (id ...dodgePP) (info "1 Chasing ghost near Pacman && follow && intersection && PP -> dodgePP") (priority 90))
    )
)


;7 chasing ghost cerca persigue, es interseccion, no cercaPP
(defrule PacmanGoWayMorePills
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman true)) 
    (PACMAN (isInIntersection true)) 
    (PACMAN (PPNear false)) 
    =>
    (assert 
        (ACTION (id ...bestWay) (info "1 Chasing ghost near Pacman && follow && intersection -> bestWay") (priority 90))
    )
)

;8 chasing ghost cerca persigue, no es interseccion
(defrule PacmanEatNextPillChasingGhostNear
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman true)) 
     (PACMAN (isInIntersection false)) 
    =>
    (assert 
        (ACTION (id ...nearestPill) (info "1 Chasing ghost near Pacman && follow && non intersection -> eatPill") (priority 90))
    )
)

;a partir de aqui no hay chasing ghosts cerca, ignoro que haya chasing ghosts cerca porque las que van con ellos tiene mas prioridad y siempre iran por delante

;9 3< edible ghosts cerca
(defrule PAcmanGoGroupEdibleGhosts
    (PACMAN (nearEdibleGhosts ?g)) (test (<= 3 ?g))
    (PACMAN (ghostFollowsPacman true)) 
     (PACMAN (isInIntersection false)) 
    =>
    (assert 
        (ACTION (id ...goGroup) (info "1 Chasing ghost near Pacman && follow && non intersection -> eatPill") (priority 80))
    )
)







(defrule PACMANnearCorner
    (MSPACMAN (distToNearestCorner < 50))
    =>
    (assert 
        (ACTION (id ...HuirRincon...) (info "Rincon Cerca ---> Huir Rincon") (priority 30))
    )
)

(defrule PACMANnearChasingNotBehind
    (MSPACMAN (distToNearestChasingNotBehind < 50))
    =>
    (assert 
        (ACTION (id ...HuirNearestChasingNotBehind...) (info "Chasing Cerca y no Misma Dir ---> Huir Chasing") (priority 30))
    )
)

(defrule PACMANnearChasingAnyDir
    (MSPACMAN (distToNearestChasingAnyDir < 50))
    =>
    (assert 
        (ACTION (id ...HuirNearestChasingAnyDir...) (info "Chasing Cerca Misma Dir ---> Huir Chasing") (priority 30))
    )
)

(defrule PACMANnearEdible
    (MSPACMAN (distToNearestEdible < 50))
    =>
    (assert 
        (ACTION (id ...ChaseEdible...) (info "Edible Cerca ---> ChaseEdible") (priority 30))
    )
)

(defrule PACMANnearPP
    (MSPACMAN (distToNearestPP < 50))
    =>
    (assert 
        (...)
    )
)

(defrule PACMANnearPill
    (MSPACMAN (distToNearestPill < 50))
    =>
    (assert 
        (...)
    )
)