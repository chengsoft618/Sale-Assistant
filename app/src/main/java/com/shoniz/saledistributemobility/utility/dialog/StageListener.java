package com.shoniz.saledistributemobility.utility.dialog;

import com.shoniz.saledistributemobility.base.download.ProgressStage;

/**
 * Created by ferdos.s on 10/4/2017.
 */

public interface StageListener {
        void OnStageGoing(ProgressStage stage, int currentTaskIndex, int allProgressCount);
        void OnStageGoing(String message);
}
