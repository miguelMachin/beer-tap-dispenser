'use client'

import { Inter } from "next/font/google";
import Dispenser from "./components/Dispenser/Dispenser";
import { Dispenser as DispenserType } from "./types/types"
import React, { useState, useEffect } from 'react'
import * as API from '@/app/misc/APIBase';
import Spinner from "./components/Spinner/Spinner"

const inter = Inter({ subsets: ['latin'] })

export default function Home() {

  const [dispensers, setDispensers] = React.useState<DispenserType[]>([]);
  const [showSpinner, setShowSpinner] = React.useState<boolean>(true);

  useEffect(() => {
    API.getDispensers().then((data) => { 
      setDispensers(data)
      setShowSpinner(false)
    }).catch((error) => console.error(error));
  }, [])

  const renerDispensers = () => {
    return (
      <>
        <div className="grid-container">
          {dispensers.map(d => {
            return (<span key={d.id}>
              {<Dispenser dispenser={d} />}
            </span>)
          })}
        </div>
      </>
    )
  }

  const renderSpinner = () => {
    return (
      
        <Spinner />
    )
  }

  return (
    <main className={inter.className}>
      <div className="container">
        {
         showSpinner ?  renderSpinner() : renerDispensers()
        }
      </div>
    </main>
  );
}
