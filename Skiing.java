import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Skiing {
  static Node[][] node;
  static short deepest = 0, steepest = 0;
  public static void main(String args[]) {
    BufferedReader reader;
    short lineIndicator = 0, xLength = 0, yLength = 0;

    try {
      reader = new BufferedReader(new FileReader("map.txt"));
      String line = reader.readLine();
      String[] shortValueXY = line.split(" ");
      xLength = Short.parseShort(shortValueXY[0]);
      yLength = Short.parseShort(shortValueXY[1]);
      String[] shortValue;

      node = new Node[xLength][yLength];
      line = reader.readLine();
      while (line != null) {
        shortValue = line.split(" ");
        for (short x = 0; x < yLength; x++) {
          node[lineIndicator][x] = new Node();
          node[lineIndicator][x].setNode(Short.parseShort(shortValue[x]));
        }
        line = reader.readLine();
        lineIndicator++;
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (short i = (short)(xLength - 1); i >= 0; i--) {
      for (short j = (short)(yLength - 1); j >= 0; j--) {
        skiing(j, i, (short)(1), node[j][i].value);
      }
    }

    System.out.println("Longest: " + deepest + ", Steepest: " + steepest);
    System.out.println("Email: " + deepest + steepest + "@redmart.com");
  }

  static void skiing(short x, short y, short depth, short deepestValue) {
    if (node[x][y].depth < depth) {
      node[x][y].depth = depth;

      if (node[x][y].deepestValue > deepestValue) {
  			node[x][y].deepestValue = deepestValue;
  		}
    }

    node[x][y].steep = (short)(node[x][y].value - node[x][y].deepestValue);

    if (depth > deepest) {
  		deepest = depth;
  		steepest = node[x][y].steep;
  	}

    if (depth == deepest) {
      if (steepest < node[x][y].steep) {
        steepest = node[x][y].steep;
      }
    }

    if (y > 0) {
      if (node[x][y].value < node[x][y-1].value) {
        skiing(x, (short)(y-1), (short)(depth+1), deepestValue);
      }
    }

    if (y < 3) {
  		if (node[x][y].value < node[x][y+1].value) {
  			skiing(x, (short)(y+1), (short)(depth+1), deepestValue);
  		}
  	}

  	if (x > 0) {
  		if (node[x][y].value < node[x-1][y].value) {
  			skiing((short)(x-1), y, (short)(depth+1), deepestValue);
  		}
  	}
  }
}

class Node {
  short value, depth, deepestValue, steep;
  void setNode (short value) {
    this.value = value;
    this.depth = 1;
    this.deepestValue = value;
    this.steep = 0;
  }
}
