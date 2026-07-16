public class FeldData
{
    private boolean[] sideBlocked;

    public boolean IsBlocked(int side)
    {
        return sideBlocked[side];
    }

    public void BlockSide(int side)
    {
        sideBlocked[side] = true;
    }
}