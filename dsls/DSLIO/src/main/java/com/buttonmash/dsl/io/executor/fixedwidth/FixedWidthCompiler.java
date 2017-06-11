package com.buttonmash.dsl.io.executor.fixedwidth;

import com.buttonmash.dsl.io.Program;
import com.buttonmash.dsl.io.tokens.IOOperation;
import com.buttonmash.dsl.io.tokens.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FixedWidthCompiler {

    public NaiveFixedWidthExecutor compile(Program program) {

        List<SortableOperation> ops = new ArrayList<>(program.getOperations().size());

        int weight = 0;

        /**
         * Dummy handler, right now just groups up the io operations
         */

        //TODO move this top generic compiling service for io opperations
        //todo methods etc this is just flat parser mode

        for (Operation op : program.getOperations()) {
            if (!(op instanceof IOOperation)) {
                weight++;
            }

            ops.add(new SortableOperation(weight, op));
        }

        Collections.sort(ops, comparator);

        List<Operation> desired = new ArrayList<>();
        for (SortableOperation op : ops) {
            desired.add(op.operation);
        }

            //todo factory (settings, etc)
        return (new NaiveFixedWidthExecutor(desired));
    }


    Comparator comparator = new Comparator<SortableOperation>() {
        @Override
        public int compare(SortableOperation o1, SortableOperation o2) {
            int c = Integer.compare(o1.weight, o2.weight);

            if (c == 0) {
                boolean o1IO = o1.operation instanceof IOOperation;
                boolean o2IO = o2.operation instanceof IOOperation;

                c = Boolean.compare(o1IO, o2IO);
            }

            return c;
        }
    };


}

class SortableOperation {
    int weight;
    Operation operation;

    public SortableOperation(int weight, Operation operation) {
        this.weight = weight;
        this.operation = operation;
    }
}
