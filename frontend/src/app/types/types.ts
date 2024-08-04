import {Status} from "../enums/Enuns";


export type DispenserSpending = {
    amount: number,
    Usages: Usage[]
}

export type Usage = {
    id: number;
    openet_at: string,
    close_at: string,
    flow_volume: number,
    total_spent: number
}

export type Dispenser = {
    id: string,
    flow_volume: number,
    amount: number,
    usages: Usage[],
    price_per_liter: number,
    status: Status
}

export type DispenserStatus = {
    status: Status
    updated_at: string
}