package com.buttonmash.dsl.io.executor.fixedwidth;

import com.buttonmash.dsl.io.Token;
import com.buttonmash.dsl.io.executor.DSLExecutor;
import com.buttonmash.dsl.io.executor.common.*;
import com.buttonmash.dsl.io.tokens.IOOperation;
import com.buttonmash.dsl.io.tokens.LengthIOOperation;
import com.buttonmash.dsl.io.tokens.Operation;
import com.buttonmash.dsl.io.tokens.PositionalIOOperation;

import java.io.Reader;
import java.text.MessageFormat;
import java.util.*;

public class NaiveFixedWidthExecutor implements DSLExecutor {
    final static int correctIndex(int idxHuman) {
        //we are 1s positioned as opposed to javas io
        return idxHuman - 1;
    }

    protected List<Operation> programOperations;

    public NaiveFixedWidthExecutor(List<Operation> ops) {
        this.programOperations = ops;
    }

    public Iterator<Map<String, Object>> processStream(Reader reader){

        List<OperationHandler> listeners = new ArrayList<>();
        listeners.add(new LogicOperationHandler());
        listeners.add(new NaiveIOOperationHandler());

        return new NaiveIterator(reader,programOperations,listeners);
    }



    class NaiveIOOperationHandler implements OperationHandler {
        Integer lastEnd = 1;

        @SuppressWarnings("unchecked")
        @Override
        public Class<? extends Operation>[] listensTo() {
            Class<? extends Operation>[] result;
            result = new Class[]{IOOperation.class};
            return result;
        }

        @Override
        public OperationResult handle(Operation operation, ReaderLine line, Map<String, Object> obj) {

            if(isAKeeper(operation)) {

                Integer ioStart = null,
                        ioEnd = null;

                IOOperation operationAsIO = (IOOperation) operation;

                if(operation instanceof LengthIOOperation){
                    //handle by length
                    Integer length = resolveToken(((LengthIOOperation)operation).getLength(), Integer.class);
                    if(length!=null) {
                        ioStart = lastEnd;
                        ioEnd = lastEnd + length;
                    }else{
                        return new OperationResult(MessageFormat.format("Argument length resolved as not set: {0}", operationAsIO.getArgs()), OperationResultTypes.ERROR);
                    }
                }else if(operation instanceof PositionalIOOperation){
                    //handle by position
                    ioStart = resolveToken(((PositionalIOOperation)operation).getStart(), Integer.class);
                    ioEnd = resolveToken(((PositionalIOOperation)operation).getEnd(), Integer.class);
                } else {
                    return new OperationResult(MessageFormat.format("Argument count unhandled : {0}", operationAsIO.getArgs()), OperationResultTypes.ERROR);
                }

                if(ioStart==null || ioEnd==null){
                    return new OperationResult(MessageFormat.format("Argument start and end are not properly set: {0}", operationAsIO.getArgs()), OperationResultTypes.ERROR);
                }

                String storeTo = resolveToken(((PositionalIOOperation) operation).getStoreTo(), String.class);

                obj.put(storeTo, readLine(line, ioStart, ioEnd));


                return OperationResult.SUCCESS;
            }

            return null;
        }


        /**
         * its kind of what all this is a wrapper of. Almost sad that this is the main thing to be abstracting here at all. Crazy isnt it?
         */
        char[] readLine(ReaderLine line, int ioStart, int ioEnd){
            return Arrays.copyOfRange(line.getChararray(), correctIndex(ioStart), correctIndex(ioEnd));
        }



        //TODO IoC
        protected boolean isAKeeper(Operation operation) {
            boolean keep = false;
            for (Class<? extends Operation> clz : listensTo()) {
                if(clz.isAssignableFrom(operation.getClass()) ){
                    keep = true;
                    break;
                }
            }
            return keep;
        }
    }


    //TODO move to service
    private <Y> Y resolveToken(Token token, Class<Y> clz) {
         throw new UnsupportedOperationException("XXX");
    }


}
