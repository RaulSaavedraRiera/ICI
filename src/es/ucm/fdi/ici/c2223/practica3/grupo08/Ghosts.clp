;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
    (slot edible(type SYMBOL))
    (slot minBLINKYDistancePPill (type NUMBER))
    (slot distanceToPacman (type NUMBER))
    (slot BLINKYremainingTime (type NUMBER))
    (slot BLINKYPosition (type NUMBER))
)

(deftemplate INKY
    (slot edible(type SYMBOL))
    (slot minINKYDistancePPill (type NUMBER))
    (slot distanceToPacman (type NUMBER))
    (slot INKYremainingTime (type NUMBER))
    (slot INKYPosition (type NUMBER))
)

(deftemplate PINKY
    (slot edible(type SYMBOL))
    (slot minPINKYDistancePPill(type NUMBER))
    (slot distanceToPacman (type NUMBER))
    (slot PINKYremainingTime(type NUMBER))
    (slot PINKYPosition (type NUMBER))
)

(deftemplate SUE
    (slot edible(type SYMBOL))
    (slot minSUEDistancePPill(type NUMBER))
    (slot distanceToPacman(type NUMBER))
    (slot SUEremainingTime(type NUMBER))
    (slot SUEPosition (type NUMBER))
)

(deftemplate ACTION
    (slot id) (slot info (default "")) (slot priority (type NUMBER)) 

)

