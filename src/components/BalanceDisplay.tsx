/**
 * File-level: Balance display component for a selected account.
 */

import React from 'react';
import { Account } from '../types';

/**
 * Props for BalanceDisplay.
 */
interface BalanceDisplayProps {
  account: Account | null;
}

/**
 * Shows current balance and basic account info.
 */
export default function BalanceDisplay({ account }: BalanceDisplayProps) {
  if (!account) {
    return (
      <div className="bg-white rounded-lg shadow p-4">
        <p className="text-sm text-gray-600">No account selected</p>
      </div>
    );
  }
  return (
    <div className="bg-white rounded-lg shadow p-4">
      <p className="text-sm text-gray-500">Account</p>
      <h3 className="text-xl font-semibold">{account.name}</h3>
      <p className="text-xs text-gray-500">{account.email}</p>
      <div className="mt-4">
        <p className="text-sm text-gray-500">Balance</p>
        <p className="text-2xl font-bold text-sky-600">${account.balance.toFixed(2)}</p>
      </div>
    </div>
  );
}