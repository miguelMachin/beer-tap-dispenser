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
  const [isErrorMessage, setIsErrorMessage] = React.useState<boolean>(false);

  useEffect(() => {
    API.getDispensers().then((data) => {
      console.log(!!data)
      if (data === undefined) {
        setIsErrorMessage(true)
        setShowSpinner(false)
      } else {
        setDispensers(data)
        setShowSpinner(false)
        setIsErrorMessage(false)
      }
    }).catch((error) => {
      console.error(error)
    });
  }, [])

  /*functions*/

  /*Renders*/

  const renderErrorMessage = () => {
    return (
      <>
        <div className="alert alert-danger" role="alert">
          An error has occurred, consult your administrator
        </div>
      </>
    )
  }

  const renerDispensers = () => {
    return (
      <>
        {isErrorMessage
          ? renderErrorMessage()
          : (<div className="grid-container">
            {dispensers.map(d => {
              return (<span key={d.id}>
                {<Dispenser dispenser={d} />}
              </span>)
            })}
          </div>

          )}
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
          showSpinner ? renderSpinner() : renerDispensers()
        }
      </div>
    </main>
  );
}
