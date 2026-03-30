package com.epam.rd.autotasks.wallet;

import java.util.List;

/**
 * Wallet creator. Is used to create a {@linkplain Wallet} instance.
 * <p/>
 * You need to specify your implementation in {@linkplain #wallet(List, PaymentLog)} method.
 */
public final class WalletFactory {

    private WalletFactory() {
        throw new UnsupportedOperationException();
    }

    /**
     * Creates new {@linkplain Wallet} instance and passes {@code accounts} and {@code log} to it.
     * <p/>
     * You must return your implementation here.
     *
     * @param accounts which will be used for payments
     * @param log which will be used to log payments
     * @return new {@linkplain Wallet} instance
     */
    public static Wallet wallet(List<Account> accounts, PaymentLog log) {

        final List<Account> accs = List.copyOf(accounts);

        return ( new Wallet() {
            @Override
            public void pay(String recipient, long amount) throws Exception {
                for (Account acc : accs) {
                    acc.lock().lock();
                    try {
                        if (acc.balance() >= amount) {
                            acc.pay(amount);
                            log.add(acc, recipient,amount);
                            return;
                        }
                    }
                    finally {
                        acc.lock().unlock();
                    }
                }
                throw (new ShortageOfMoneyException(recipient, amount));
            }
        });
    }

}
