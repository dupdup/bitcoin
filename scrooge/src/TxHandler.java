public class TxHandler {

    UTXOPool utxoPool;
    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */
    public TxHandler(UTXOPool utxoPool) {
         utxoPool = new UTXOPool(utxoPool);
        // IMPLEMENT THIS
    }

    /**
     * @return true if:
     * (1) previous outputs claimed by {@code tx} are in the current UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid, 
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {
        // IMPLEMENT THIS
//
//You can create a new UTXO object from any input of a transaction and
// then you can obtain the output referred from your current pool or
// any other temporary pool you have created, if it is contained.
//        UTXO utxo = new UTXO(input.prevTxHash, input.outputIndex);
//        Transaction.Output output = utxoPool.getTxOutput(utxo);
        boolean signature = tx.getInputs().stream().anyMatch((Transaction.Input i)->{
            Transaction.Output o = tx.getOutput(i.outputIndex);
            return Crypto.verifySignature(o.address,tx.getHash(),i.signature);
        });
        if(!signature) return false;
        double sumOfOut = tx.getOutputs().stream().mapToDouble((Transaction.Output t)->t.value).sum();
        return true;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS

        return null;
    }

}
