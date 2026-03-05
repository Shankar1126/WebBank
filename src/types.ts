/**
 * File-level: Shared TypeScript types for the banking demo app.
 */

/**
 * Interface describing a user account.
 */
export interface Account {
  id: string;
  name: string;
  email: string;
  balance: number;
  createdAt: string;
}

/**
 * Interface describing a financial transaction.
 */
export interface Transaction {
  id: string;
  type: 'transfer' | 'receive' | 'deposit' | 'card_payment' | 'card_apply';
  amount: number;
  date: string;
  from?: string;
  to?: string;
  note?: string;
}

/**
 * Interface describing a banking card.
 */
export interface BankCard {
  id: string;
  last4: string;
  type: 'debit' | 'credit';
  status: 'active' | 'pending' | 'declined';
  createdAt: string;
  accountId: string;
}