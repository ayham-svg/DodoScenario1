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
  
  public void simpleMaze () {      //  zo lang je nie op nest bent draai naar recht kun je door ga door anders draai links kun je door ga door anders draai links 
      while (!onNest()) {
          turnRight();
          if (canMove()) {
              move();          } else {
              turnLeft();
              while (!canMove()) {
                  turnLeft();
              }
              move();
        }
      }
      showCompliment("Nest gevonden!");
}


public void prepareWorld() { // legt eiren op oneven rijen 
    int width = getWorld().getWidth();
    int height = getWorld().getHeight(); 
    int y = 0;
    while (y < height) {
        goToLocation(0, y);
        faceEast();
        int eggs = countEggsInRow();
        if (eggs % 2 != 0) {           
            goToLocation(width - 1, y);
            layEgg();
        }
        y++;
    }
    int x = 0;
    while (x < width) {
        goToLocation(x, 0);
        faceSouth();
        int eggs = countEggsInRow();  
        if (eggs % 2 != 0) {
            goToLocation(x, height - 1);
            layEgg();
        }
        x++;
    }
}

public void zoekFout() {  // zoekt fout tussen x en y as en legt ei op kruisput 
    int width = getWorld().getWidth();
    int height = getWorld().getHeight();
    int rij = -1;    
    int kolom = -1;
    int y = 0;
    while (y < height) {
        goToLocation(0, y);
        faceEast();
        if (countEggsInRow() % 2 != 0) {
            rij = y;
        }
        y++;
    }
    int x = 0;
    while (x < width) {
        goToLocation(x, 0);
        faceSouth();
        if (countEggsInRow() % 2 != 0) {
            kolom = x;
        }
        x++;
    }
    if (rij == -1) {
        showCompliment("Geen fout gevonden!");
    } else {
        goToLocation(kolom, rij);
        showError("Fout bij (" + kolom + ", " + rij + ")");
    }
}




  public void faceEgg() {    // draai naar ei toe  
    turn180();
      turnRight();                                                                                                                                        
      while (!eggAhead()) {
          turnRight();
      }
  }
  
  public void face(int direction) {  // draai in de richting van 0 tot 3 
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

  public void eggTrailToNest() { // volg de eiren met behulp van de fuctie face egge en move tot je bijnest uitkomt 
      while (!onNest()) {
          faceEgg();
          move();
      }
  }
  
public void climbOverFence() {  // klim over een hekjee
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
  
  public void herstelWereld() {     // zoek de fout van oneven kolom en rij en leg ei of haal weg 
    int width = getWorld().getWidth();
    int height = getWorld().getHeight();
    int rij = -1;
    int kolom = -1;

    int y = 0;
    while (y < height) {
        goToLocation(0, y);
        faceEast();
        if (countEggsInRow() % 2 != 0) {
            rij = y;
        }
        y++;
    }

    int x = 0;
    while (x < width) {
        goToLocation(x, 0);
        faceSouth();
        if (countEggsInRow() % 2 != 0) {
            kolom = x;
        }
        x++;
    }

    if (rij == -1) {
        showCompliment("Geen fout gevonden!");
    } else {
        goToLocation(kolom, rij);
        if (onEgg()) {
            pickUpEgg();
        } else {
            layEgg();
        }
        showCompliment("Wereld hersteld!");
    }
}

public void gaNaarHoek() {
    walkToWorldEdge();   // tegen eeen rand
    turnRight();
    walkToWorldEdge();   // nu in een hoek
    turn180();           // kijk de wereld weer in
}

public boolean volgendeLijn() {   // stap naar de volgende rijenkolom
    turnRight();
    if (borderAhead()) {          // geen lijn meer
        turnLeft();
        return false;
    }
    move();
    turnLeft();
    return true;
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
  
  public void gotoEgg() { // ga recht door tot je op ei zit 
      while (!onEgg()) {
          move();
      }
  }
  
  public void layEggInEmptyNests() { // move recht door zolang geen border ahead leg een eig op het nest zolang geen ei er in zit 
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
  
  
  public void pyramidOfEggs() {   // maak een piramide zo vermogeijk 
      int startX = getX();
      int startY = getY();
      int height = getWorld().getHeight() - startY;
      int row = 0;

      while (row < height && startX - row >= 0) {
          goToLocation(startX - row, startY + row);
          faceEast();
          layTrailOfEggs(2 * row + 1);
          row++;
      }

      goToLocation(startX, startY);
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
  
  public void walkToWorldEdgeClimbingOverFencesAndLayEgg() { // ga naar het eiende van de wereld klim over hekken tot aan bij nest en leg een ei 
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
              face(1);
              move();
          } else if (getX() > coordX) {
              face(3);
              move();
          } else if (getY() < coordY) {
              face(2);
              move();
          } else if (getY() > coordY) {
              face(0);
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
  
  public void layTrailOfEggs(int n) {  
      if (n <= 0) {
          showError("Ongeldig aantal");
          return;
      }
      int count = 0;
      while (count < n) {
          if (canLayEgg()) {
              layEgg();
          }
          count++;
          if (count < n) {
              move();
          }
      }
  }
  
  public int countAllEggs() { // tel eiren in de wereld cotroleer eerst hoogte breede van de wereld 
      int totalEggs = 0;
      int height = getWorld().getHeight();
      int row = 0;

      while (row < height) {
          goToLocation(0, row);
          face(1);
          int eggsInRow = countEggsInRow();
          totalEggs += eggsInRow;
          row++;
      }

      goToLocation(0, 0);
      System.out.println("Totaal: " + totalEggs);
      return totalEggs;
  }
  
  
   public double averageEggsPerRow() {  // tell alle eiren en geef de gemiddelde per rij
      int totalEggs = countAllEggs();
      int height = getWorld().getHeight();
      double average = (double) totalEggs / height;
      System.out.println("Gemiddelde eieren per rij: " + average);
      return average;
  }
  
   public int countEggsInRow() { // tel eiren in een rij
      int count = 0;
      if (onEgg()) { count++; }
      while (!borderAhead()) {
          move();
          if (onEgg()) { count++; }
      }
      goBackToStartOfRowAndFaceBack();
      return count;
  }
  
  public void mounumentOfEggs(){    //  maak het monumet van eiren tot niet meer kan
   int startX = getX();
   int startY = getY();
   int height = getWorld().getHeight();
   int row = 0;
   while (row < height - startY) {
    goToLocation(startX, startY + row);
    face(1);
    layTrailOfEggs(row + 1);
    row++;
    }
    goToLocation(startX,startY);
    face(1);
    }
    
public void mounumentOfeggs(){  // maak monumetn tot niet meer kan
   int startX = getX();
   int startY = getY();
   int height = getWorld().getHeight();
   int count = 1;
   int row = 0;
   while (row < height - startY  && count <= getWorld().getWidth() - startX) {
    goToLocation(startX, startY + row);
    face(1);
    layTrailOfEggs(count);
    count = count * 2;
    row++;
    }
    goToLocation(startX,startY);
    face(1);
    }
  
  
  
  public void walkAroundFencedArea() {  //  loop om fences heen door wile not on egge draai heltijd naar recht tot je kunt moven anders draai je links en move draai recht move enzovoort 
      while (!onEgg()){
      turnRight();
      if (fenceAhead()){
          turnLeft();
        }
        move();
  }
}

public int rowWithMostEggs() {    // vijnd rij met meeste eiren 
      int maxEggs = 0;
      int bestRow = 0;
      int height = getWorld().getHeight();                                                                             
      int row = 0;

      while (row < height) {
          goToLocation(0, row);
          face(1);
          int eggsInRow = countEggsInRow();
          System.out.println("Rij " + row + ": " + eggsInRow + " eieren");
          if (eggsInRow > maxEggs) {
              maxEggs = eggsInRow;
              bestRow = row;
          }
          row++;
      }  
      return(row);
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
    public void walkToWorldEdge() {  // loop helemaal naar het einde van de wereld 
        while (!borderAhead()){
            move();
        }
    }
}
