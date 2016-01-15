package sample;

import java.util.*;


public interface MoveListener extends EventListener {
    void playerIsMoving(float pPosX, float pPosY);
}