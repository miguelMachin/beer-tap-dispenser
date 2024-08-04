'use client'

import React, { useState, useEffect } from 'react'
import * as API from '@/app/misc/APIBase';
import { Dispenser as DispenserType } from '../../types/types';
import Image from 'next/image'
import styles from './Dispender.module.css'
import { Status } from './../../enums/Enuns'

type dispenserProps = {
    dispenser: DispenserType,
}


const Dispenser: React.FC<dispenserProps> = ({ dispenser }) => {
    const [buttonLabel, setButtonLabel] = React.useState<string>("PUSH");
    const [spending, setSpending] = React.useState<number>(0);
    const [amount, setAmount] = React.useState<number>(0);
    const [timer, setTimer] = React.useState<any>();

    const handlerUpdateStatus = () => {
        
        let status = dispenser.status
        if (dispenser.status === Status.CLOSE) {
            status = Status.OPEN;
            setButtonLabel("STOP");
            startInterval();
        } else if (dispenser.status === Status.OPEN) {
            status = Status.CLOSE;
            setButtonLabel("PUSH");
        }
        

        API.updateStatusDispenser(dispenser.id, status).then((data) => {
            dispenser.status = data.status;
            stopInterval();
        }).catch((err) => console.log(err))
    }

    const startInterval = () => {
        let count = 0;
        setSpending(0);
        setAmount(0);
        setTimer(
            setInterval(() => {
                count++;
                let amount = dispenser.flow_volume * count;
                let spending = dispenser.price_per_liter * amount;
                setAmount(amount);
                setSpending(spending);
                
            }, 1000)
        )
    }

    const stopInterval = () => {
        clearInterval(timer);
    }


    return (
        <div className={styles.card}>
            <div>
                <Image src="/logo.png"
                    width={300}
                    height={300}
                    alt="Duff beer" />
            </div>
            <div className={styles.container_detail}>
                <div className={styles.container_border}>
                    <div className={styles.box}>
                        <div className={styles.title_box}>Amount</div>
                        <hr className={styles.hr} />
                        <div>{Math.trunc(amount * 1000)} ml</div>
                    </div>
                    <div className={styles.box}>
                        <div className={styles.title_box}>Price</div>
                        <hr className={styles.hr} />
                        <div>{spending.toFixed(3)} €</div>
                    </div>
                </div>
            </div>
            <div className={styles.container_button}>
                <button type="button" className="btn btn-danger" onClick={() => handlerUpdateStatus()}>{buttonLabel} </button>
            </div>
        </div>
    )

}

export default Dispenser;