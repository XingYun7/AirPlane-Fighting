package graphic_Z.HUDs;

import java.awt.event.KeyEvent;

import graphic_Z.Common.Operation;
import graphic_Z.utils.Common;

public class CharButton extends CharLabel implements Operable, Widget {

    private CharLabel    outerBoxSelected;
    private final String outerBoxSelectedText;
    private boolean      selected;
    private Operable     caller;
    private int          sizeX;
    private int          locationSource[];
    
    public CharButton(
        char[][] frapsBuffer, 
        int[] scrResolution, 
        String Text, 
        int locationX, 
        int locationY,
        Operable caller
    ) { this(frapsBuffer, scrResolution, Text, locationX, locationY, Text.length(), caller); }
    
    public CharButton(
        char[][] frapsBuffer, 
        int[] scrResolution, 
        String Text, 
        int locationX, 
        int locationY, 
        int sizeX,
        Operable caller
    ) {
        super(
            frapsBuffer,
            (int)(Math.random() * 32767), 
            scrResolution, 
            Text, 
            sizeX > Text.length()? 
                    locationX + ((sizeX - Text.length()) >> 1)
                :
                    locationX, 
            locationY, 
            false
        );
        
        if(sizeX < Text.length()) sizeX = Text.length();
        
        outerBoxSelectedText = 
        " -" + Common.loopChar('-', sizeX) + "- \n" +
        "/ " + Common.loopChar(' ', sizeX) + " \\\n" +
        "> " + Common.loopChar(' ', sizeX) + " <\n" +
        "\\ " + Common.loopChar(' ', sizeX) + " /\n"+
        " -" + Common.loopChar('-', sizeX) + "- ";
        
        outerBoxSelected = new CharLabel(
            frapsBuffer,          layer,         scrResolution, 
            outerBoxSelectedText, locationX - 2, locationY - 2
        );
        selected    = false;
        this.caller = caller;
        this.sizeX  = sizeX;
        locationSource = new int[] {locationX, locationY};
    }
    
    @Override
    public void printNew() {
        this.printNew(selected);
        super.printNew();
    }
    
    public void printNew(boolean selected) {
        if(selected) outerBoxSelected.printNew();
        setSelected(false);
    }
    
    @Override
    public void setText(String s) {
        super.setText(s);
        super.setLocation(
            sizeX > s.length()? 
                locationSource[0] + ((sizeX - s.length()) >> 1)
              :
                locationSource[0], 
            locationSource[1] 
        );
    }
    
    @Override
    public Operation call() {
        if(!selected) return null;
        return caller.call();
    }
    
    @Override
    public void resetScreenBuffer(char fraps_buffer[][]) {
        super.resetScreenBuffer(fraps_buffer);
        outerBoxSelected.resetScreenBuffer(fraps_buffer);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean getInput(int keyChar) {
        return false;
    }

    @Override
    public boolean getControl(int keyCode) {
        if(keyCode == KeyEvent.VK_ENTER) {
            call();
            return true;
        }
        return false;
    }
}
