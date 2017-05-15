import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TxHandler {

    UTXOPool utxoPool;
    UTXOPool rawPool;
    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
         utxoPool = new UTXOPool(utxoPool);
         rawPool = new UTXOPool(utxoPool);
        // IMPLEMENT THIS
    }

    /**
     * @return true if:
     * (1) previous outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4*) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
        // IMPLEMENT THIS
//
//You can create a new UTXO object from any input of a transaction and
// then you can obtain the output referred from your current pool or
// any other temporary pool you have created, if it is contained.
        boolean signature = tx.getInputs().stream().anyMatch((Transaction.Input input)->{
            UTXO utxo = new UTXO(input.prevTxHash, input.outputIndex);
            Transaction.Output o = utxoPool.getTxOutput(utxo);
            if(o == null) return false;
            return Crypto.verifySignature(o.address,input.prevTxHash,input.signature);

        });
        if(!signature) return false;

        if(isOutputsNonNegative(tx)) return false;
        double sumOfIn = tx.getInputs().stream().mapToDouble((input)->{
            UTXO utxo = new UTXO(input.prevTxHash, input.outputIndex);
            return utxoPool.getTxOutput(utxo).value;
        }).sum();
        double sumOfOut = tx.getOutputs().stream().mapToDouble((t)->t.value).sum();
        return true;
    }
    public boolean isOutputsNonNegative(Transaction tx){
        return tx.getOutputs().stream().anyMatch((o)->{return o.value<0;});
    }
    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
//<<<<<<< HEAD
        // IMPLEMENT THIS
        Transaction[] accepted = new Transaction[possibleTxs.length];
        Arrays.stream(possibleTxs).forEach((Transaction t)->{
            UTXOPool tp = new UTXOPool();
            t.getOutputs().forEach((Transaction.Output o) -> {
                UTXO ut = new UTXO(t.getRawTx(), t.getOutputs().indexOf(o));
                if(!this.rawPool.contains(ut) && !tp.contains(ut)){
                    tp.addUTXO(ut,o);
                }
            });
            tp.getAllUTXO().forEach((UTXO u )->this.rawPool.addUTXO(u,tp.getTxOutput(u)));
        });
//        while(true) {
            for (Transaction possibleTx : possibleTxs) {
                if (!isValidTx(possibleTx)) {

                }
            }
//=======
        List<Transaction> invalidTxs = new LinkedList<>();
        Arrays.stream(possibleTxs).forEach((tx -> {
            if(isOutputsNonNegative(tx)){
                tx.getOutputs().stream().forEach((o)->{
                    utxoPool.addUTXO(new UTXO(tx.getHash(),tx.getOutputs().indexOf(o)),o);
                });
            }else{
                invalidTxs.add(tx);
            }
        }));
//        Arrays.stream(possibleTxs).forEach((tx -> {
//            if(!invalidTxs.contains(tx)&&isValidTx(tx)){
//                tx.getInputs().stream().forEach((i)->{
//                    utxoPool.removeUTXO(new UTXO(i.prevTxHash,i.outputIndex));
//                });
//                tx.getOutputs().stream().forEach((o)->{
//                    utxoPool.addUTXO(new UTXO(tx.getHash(),tx.getOutputs().indexOf(o)),o);
//                });
//            }
//        }));

//        }
        return accepted;
    }

}
