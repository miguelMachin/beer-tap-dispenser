'use client'

import React, { useState, useEffect } from 'react'
import { getDispenser } from '@/app/misc/APIBase';


export default function Dispenser() {
    useEffect(() => {
        getDispenser("a");
      }, [])


    return (
        <div>
            AAAAAAAAAAAAAA
            
        </div>
    )

}