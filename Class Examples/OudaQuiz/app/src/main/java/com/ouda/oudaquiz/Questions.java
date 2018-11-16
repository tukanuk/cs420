package com.ouda.oudaquiz;

/**
 * Created by abdel on 2018-01-29.
 */

public class Questions {


        private int qID;
        private boolean qAns;

        public Questions (int in_qID, boolean in_QAns) {
            qID = in_qID;
            qAns = in_QAns;
        }

        public int getQID () {
            return qID;
        }

        public void setQID (int in_qID) {
            qID = in_qID;
        }

        public boolean getQAns() {
            return qAns;
        }

        public void setQAns(boolean in_QAns) {
            qAns = in_QAns;
        }

}
