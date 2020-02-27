package com.gable.dos.ddq.controller.accessLocal;

import com.gable.dos.ddq.model.MainOutput;
import lombok.Data;

@Data
public class ModelFile extends MainOutput {
    private String Full_FileName;
    private String FileName;
    private String Size;
    private String DateTime;
    private String Type;
}
