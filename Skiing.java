import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Skiing {
  static Node[][] node;
  static short deepest = 0, steepest = 0, xLength = 0, yLength = 0;
  public static void main(String args[]) {
    BufferedReader reader;
    short lineIndicator = 0;

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

    for (short i = 0; i < (short)(xLength); i++) {
      for (short j = 0; j < (short)(yLength); j++) {
        skis(i, j, (short)(1));
      }
    }
    System.out.println("Longest: " + deepest + ", Steepest: " + steepest);
    System.out.println("Email: " + deepest + steepest + "@redmart.com");
  }

  static Node skis(short x, short y, short depth) {
    Node n = new Node();
    short leftDepth = 0, rightDepth = 0, bottomDepth = 0, deepestValue = node[x][y].value, leftDeepestValue = 0, bottomDeepestValue = 0, rightDeepestValue = 0;

    if (y > 0) {
      if (node[x][y].value > node[x][y-1].value) {
        n = skis(x, (short)(y-1), (short)(depth+1));
        leftDepth += n.depth;
        leftDeepestValue = n.deepestValue;
      } else {
        leftDeepestValue = node[x][y].value;
      }
    }

    if (x < (short)(xLength-1)) {
      if (node[x][y].value > node[x+1][y].value) {
        n = skis((short)(x+1), y, (short)(depth+1));
        bottomDepth += n.depth;
        bottomDeepestValue = n.deepestValue;
      } else {
        bottomDeepestValue = node[x][y].value;
      }
    }

    if (y < (short)(yLength-1)) {
      if (node[x][y].value > node[x][y+1].value) {
        n = skis(x, (short)(y+1), (short)(depth+1));
        rightDepth += n.depth;
        rightDeepestValue = n.deepestValue;
      } else {
        rightDeepestValue = node[x][y].value;
      }
    }

    if (depth < leftDepth) {
      depth = leftDepth;
      deepestValue = leftDeepestValue;
    }

    if (depth < bottomDepth) {
      depth = bottomDepth;
      deepestValue = bottomDeepestValue;
    }

    if (depth < rightDepth) {
      depth = rightDepth;
      deepestValue = rightDeepestValue;
    }

    node[x][y].deepestValue = n.deepestValue = deepestValue;
    node[x][y].depth = n.depth = depth;
    node[x][y].steep = (short)(node[x][y].value - deepestValue);

    if (deepest == node[x][y].depth) {
      if (steepest < node[x][y].steep) {
        steepest = node[x][y].steep;
      }
    }

    if (deepest < node[x][y].depth) {
      deepest = node[x][y].depth;
      steepest = node[x][y].steep;
    }

    return n;
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
