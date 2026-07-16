
/**
 * Beschreiben Sie hier die Klasse Direction.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Direction
{
    int up;
    int down;
    int left;
    int right;

    
    public Direction()
    {
        up = down = left = right = 0;
    }

    public Direction(int Up, int Down, int Left, int Right)
    {
        up = Up;
        down = Down;
        left = Left;
        right = Right;
    }
}
