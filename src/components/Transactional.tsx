/**
 * File-level: Transaction list component to show recent activity.
 */

import React from 'react';
import { Transaction } from '../types';

/**
 * Props for TransactionList.
 */
interface TransactionListProps {
  transactions: Transaction[];
}

/**
 * TransactionList renders a simple list of transactions.
 */
export default function TransactionList({ transactions }: TransactionListProps) {
  return (
    <div className="bg-white rounded-lg shadow p-4">
      <h4 className="font-semibold mb-2">Recent Transactions</h4>
      {transactions.length === 0 ? (
        <p className="text-sm text-gray-500">No transactions yet</p>
      ) : (
        <ul className="space-y-2 max-h-64 overflow-auto">
          {transactions.map(tx => (
            <li key={tx.id} className="flex justify-between items-center">
              <div>
                <div className="text-sm font-medium">{tx.type.toUpperCase()}</div>
                <div className="text-xs text-gray-500">{tx.note || `${tx.from || ''} → ${tx.to || ''}`}</div>
              </div>
              <div className="text-right">
                <div className={`font-semibold ${tx.type === 'receive' ? 'text-green-600' : 'text-red-600'}`}>
                  {tx.type === 'receive' ? '+' : '-'}${tx.amount.toFixed(2)}
                </div>
                <div className="text-xs text-gray-400">{new Date(tx.date).toLocaleString()}</div>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}