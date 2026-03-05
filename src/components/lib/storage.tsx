/**
 * File-level: Local storage helpers for accounts, transactions and cards.
 */

// @ts-ignore
import { Account, Transaction, BankCard } from '../types';

/**
 * Get persisted accounts from localStorage.
 * @returns Account[]
 */
export function loadAccounts(): Account[] {
  try {
    const raw = localStorage.getItem('bank_accounts');
    return raw ? (JSON.parse(raw) as Account[]) : [];
  } catch {
    return [];
  }
}

/**
 * Persist accounts to localStorage.
 * @param accounts Account[]
 */
export function saveAccounts(accounts: Account[]) {
  localStorage.setItem('bank_accounts', JSON.stringify(accounts));
}

/**
 * Get persisted transactions from localStorage.
 * @returns Transaction[]
 */
export function loadTransactions(): Transaction[] {
  try {
    const raw = localStorage.getItem('bank_transactions');
    return raw ? (JSON.parse(raw) as Transaction[]) : [];
  } catch {
    return [];
  }
}

/**
 * Persist transactions to localStorage.
 * @param txs Transaction[]
 */
export function saveTransactions(txs: Transaction[]) {
  localStorage.setItem('bank_transactions', JSON.stringify(txs));
}

/**
 * Get persisted bank cards from localStorage.
 * @returns BankCard[]
 */
export function loadCards(): BankCard[] {
  try {
    const raw = localStorage.getItem('bank_cards');
    return raw ? (JSON.parse(raw) as BankCard[]) : [];
  } catch {
    return [];
  }
}

/**
 * Persist bank cards to localStorage.
 * @param cards BankCard[]
 */
export function saveCards(cards: BankCard[]) {
  localStorage.setItem('bank_cards', JSON.stringify(cards));
}