import {Status} from "./Enuns";


export interface DispenserSpending {
    amount: number;
    Usages: Usage[];
}

export interface Usage {
    id: number;
    openet_at: Date;
    close_at: Date;
    flow_volume: number;
    total_spent: number;
}

export interface Dispenser {
    id: String;
    flow_volume: number;
    amount: number;
    usages: Usage[];
    price_per_liter: number;
    status: Status
}