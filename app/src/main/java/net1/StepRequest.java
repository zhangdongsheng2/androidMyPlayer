package net1;

/**
 * @Author 冯高远
 * @Date 2016/7/29 15:07
 * @Description
 */
public class StepRequest extends BasicRequest {
    private boolean isAddNew;
    private boolean isFinalStep;

    public boolean isAddNew() {
        return isAddNew;
    }

    public void setAddNew(boolean addNew) {
        isAddNew = addNew;
    }

    public boolean isFinalStep() {
        return isFinalStep;
    }

    public void setFinalStep(boolean finalStep) {
        isFinalStep = finalStep;
    }
}
