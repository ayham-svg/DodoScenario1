import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    
    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    
    
public boolean canMove() {
          if (borderAhead()) {
              return false;
          } else if (fenceAhead()) {
              return false;
          } else {
              return true;
          }
      }
      
    public void turn180() { // draai 180 graden 
      turnRight();                                                                                                                                                                           
      turnRight();
  }
  
 
  
  //public boolean eggAhead() {
  //    step();
  //    boolean found = onEgg() || onNest();
  //    stepOneCellBackwards();
  //return found;
 // }
  
  public void eggTrailToNestSlordig() {
      while (!onNest()) {
          if (eggAhead() || nestAhead()) {
              move();
          } else {
              turnRight();
              if (!eggAhead()) {
                  turn180();
                }
          }
      }
  }
  
  public void simpleMaze () {  
      while (!onNest()) {
          turnRight();
          if (canMove()) {
              move();
          } else {
              turnLeft();
              while (!canMove()) {
                  turnLeft();
              }
              move();
          }
      }
      showCompliment("Nest gevonden!");
}

  public void faceEgg() {
      turn180();
      turnRight();                                                                                                                                        
      while (!eggAhead()) {
          turnRight();
      }
  }
  
  public void face(int direction) {
      if (direction >= 0 && direction <= 3) {
          while (getDirection() != direction) {
              turnRight();
          }
      }
  }
    
public void faceEast()  { face(EAST);  }
public void faceWest()  { face(WEST);  }
public void faceNorth() { face(NORTH); }
public void faceSouth() { face(SOUTH); }

  public void eggTrailToNest() {
      while (!onNest()) {
          faceEgg();
          move();
      }
  }
  
public void climbOverFence() {
    turnLeft();
    step();
    turnRight();
    step();
    step();
    turnRight();
    step();
    turnLeft();
}

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

     public boolean grainAhead() {   //chekt of er een grain voor hem staat en draait om en gaat terug
      step();
      boolean grainFound = onGrain();
      stepOneCellBackwards();
      return grainFound;
  }
    
  public void stepOneCellBackwards() {  // een stap achter uit 
      turn180();
      step();
      turn180();
  }
  
    public void walkToWorldEdgeClimbingOverFences() {   // loop naar de rand en klim over fences
      while (!borderAhead()) {  
          if (fenceAhead()) {
              climbOverFence();
          } else {
              move();
          }
      }
  }
  
  public void pickUpGrainsAndPrintCoordinates() { // dodo loop naar vooren en onder weg pakt ie graan op en prinie cordinaten
      while (!borderAhead()) {
          if (onGrain()) {
              System.out.println(getX() + ", " + getY());
              pickUpGrain();
          }
          move();
      }
      if (onGrain()) {
          System.out.println(getX() + ", " + getY());
          pickUpGrain();
      }
  }
  
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){   // loop door tot je border ziet
        while( ! borderAhead() ){ 
            move();
        }
    }
    
public void goBackToStartOfRowAndFaceBack() {   // draai om en en speel walktoworldsedge inprincipe gaat ie terug naar de start van  de rij
      turn180();
      walkToWorldEdge();
      turn180();
  }
  
  public void gotoEgg() {
      while (!onEgg()) {
          move();
      }
  }
  
  public void layEggInEmptyNests() {
      while (!borderAhead()) {
          if (onNest() && !onEgg()) {
              layEgg();
          }
          move();
      }
      if (onNest() && !onEgg()) {
          layEgg();
      }
  }
  
   public void walkAroundOtherFencedArea() { // extra turnLeft() toegevoegd en while loop  
      while (!onEgg()) {
          turnRight();
          if (canMove()) {
              move();
          } else {
              turnLeft();
              while (!canMove()) {
                  turnLeft();
              }
              move();
          }
      }
  }
  
  public boolean locationReached(int x, int y) {
      return getX() == x && getY() == y;
  }
  
  public void walkToWorldEdgeClimbingOverFencesAndLayEgg() {
      while (!borderAhead()) {
          if (fenceAhead()) {
              climbOverFence();
          } else {
              move();
          }
          if (onNest() && canLayEgg()) {
              layEgg();
          }
      }
  }
  
   public void goToLocation(int coordX, int coordY) {  // vind coodinaten bijv als is te klein is 4.2 stap oost 5.2 of als zuid te klein is 5.2 stap zuid 5.3
       
      while (!locationReached(coordX, coordY)) {
          if (getX() < coordX) {
              setDirection(EAST);
              move();
          } else if (getX() > coordX) {
              setDirection(WEST);
              move();
          } else if (getY() < coordY) {
              setDirection(SOUTH);
              move();
          } else if (getY() > coordY) {
              setDirection(NORTH);
              move();
          }
      }
  }
  
  public boolean validCoordinates(int x, int y) {  /// hier controleer ik of de codinaten kloppen met de wereld greenfoot begint met 0 blijk baat dus ik doe dan niet lager dan 0 en niet hoger dan de waarde die ik terug krijg van getWidth   en dat doe ik ook voor height 
      int width = getWorld().getWidth();
      int height = getWorld().getHeight();
      if (x >= 0 && x < width && y >= 0 && y < height) {
          return true;
      } else {
          showError("Invalid coordinates");
          return false;
      }
  }    
  
  public void walkAroundFencedArea() {
      while (!onEgg()){
      turnRight();
      if (fenceAhead()){
          turnLeft();
        }
        move();
  }
}  
  
    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){ //  checkt of er al een ei aanwezig is zo niet mag hij er een leggen 
        if( onEgg() ){
            return false;
       }else{
            return true;
        }
    }  
    public void walkToWorldEdge() {
        while (!borderAhead()){
            move();
        }
    }
}
