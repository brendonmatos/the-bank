
/*
 * -------------------------------------------------------
 * THIS FILE WAS AUTOMATICALLY GENERATED (DO NOT MODIFY)
 * -------------------------------------------------------
 */

/* tslint:disable */
/* eslint-disable */

export enum MovementType {
    INCOME = "INCOME",
    EXPENSE = "EXPENSE"
}

export enum MovementCategory {
    FOOD = "FOOD",
    TRANSPORT = "TRANSPORT",
    ENTERTAINMENT = "ENTERTAINMENT",
    OTHER = "OTHER"
}

export enum MovementCurrency {
    EUR = "EUR",
    USD = "USD",
    GBP = "GBP",
    BRL = "BRL"
}

export enum MovementStatus {
    PENDING = "PENDING",
    CONFIRMED = "CONFIRMED",
    CANCELLED = "CANCELLED"
}

export interface MovementListQuery {
    limit?: Nullable<number>;
    offset?: Nullable<number>;
    startDate?: Nullable<Date>;
    endDate?: Nullable<Date>;
    text?: Nullable<string>;
    category?: Nullable<string>;
    type?: Nullable<string>;
}

export interface Account {
    id: string;
    name: string;
    balance: number;
    currency: string;
    movements: Movement[];
}

export interface Movement {
    id: string;
    date: Date;
    description: string;
    amount: number;
    transactionId: string;
    receipt?: Nullable<Receipt>;
    type: MovementType;
    category: MovementCategory;
    currency: MovementCurrency;
    status: MovementStatus;
    account: Account;
}

export interface Receipt {
    id: string;
    url: string;
}

export interface IQuery {
    list(query?: Nullable<MovementListQuery>): Movement[] | Promise<Movement[]>;
    account(id: string): Account | Promise<Account>;
}

type Nullable<T> = T | null;
