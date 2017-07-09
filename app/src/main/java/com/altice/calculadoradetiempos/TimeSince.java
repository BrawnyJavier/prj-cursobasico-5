package com.altice.calculadoradetiempos;

import android.content.res.Resources;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Javier on 7/5/2017.
 */

class TimeSince {

    private Date timeInit;
    private Date timeFin;
    private Resources resources; // Hosts the resources from the intent that instanced
    private double seconds;

    private TimeSince(Date init, Date fin, Resources resourcese) {
        this.timeFin = fin;
        this.timeInit = init;
        this.resources = resourcese;
        seconds = getDateDiff(timeInit, timeFin, TimeUnit.SECONDS);

    }

    // algorithm to compute the string. All strings to be
    // outputted come from the resources of the intent,
    // thus making it translatable.

    String getTimeSince() {
        String intervalType;
        double interval = (seconds / 31536000);

        if (interval >= 1) {
            intervalType = resources.getString(R.string.año);
        } else {
            interval = (seconds / 2592000);
            if (interval >= 1) {
                intervalType = resources.getString(R.string.mes);
            } else {
                interval = (seconds / 86400);
                if (interval >= 1) {
                    intervalType = resources.getString(R.string.dia);
                } else {
                    interval = (seconds / 3600);
                    if (interval >= 1) {
                        intervalType = resources.getString(R.string.hora);
                    } else {
                        interval = (seconds / 60);
                        if (interval >= 1) {
                            intervalType = resources.getString(R.string.minuto);
                        } else {
                            intervalType = resources.getString(R.string.segundo);
                        }
                    }
                }
            }
        }
        if (interval % 1 != 0) {         // Si no es un intervalo exacto
            // Cambia los segundos por el restante del intervalo
            seconds = interval % 1;
            if (intervalType.equals(resources.getString(R.string.año)))
                seconds = seconds * 31536000;
            else if (intervalType.equals(resources.getString(R.string.mes)))
                seconds = seconds * 2592000;
            else if (intervalType.equals(resources.getString(R.string.dia)))
                seconds = seconds * 86400;
            else if (intervalType.equals(resources.getString(R.string.hora)))
                seconds = seconds * 3600;
            else if (intervalType.equals(resources.getString(R.string.minuto)))
                seconds = seconds * 60;

            if (interval > 1 || interval == 0) {
                intervalType = getPlural(intervalType);
            }
            if (!intervalType.equals(resources.getString(R.string.segundo)))
                // retorna el valor obtenido en ey sta llamada, y
                // ejecuta recursivamente con los segundos modificados del restante
                return ((int) Math.floor(interval)) + " " + intervalType + ", " + getTimeSince();
        } else if (intervalType.isEmpty()) return "";
        if (interval > 1 || interval == 0) {
            intervalType = getPlural(intervalType);
        }
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(interval) + " " + intervalType;

    }

    /**
     * Get a diff between two dates
     *
     * @param date1    the oldest date
     * @param date2    the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    private String getPlural(String intervalType) {


        if (intervalType.equals(resources.getString(R.string.año)))
            intervalType = resources.getString(R.string.yearPlural);

        else if (intervalType.equals(resources.getString(R.string.mes)))
            intervalType = resources.getString(R.string.mesPlural);

        else if (intervalType.equals(resources.getString(R.string.dia)))
            intervalType = resources.getString(R.string.diasPlural);

        else if (intervalType.equals(resources.getString(R.string.hora)))
            intervalType = resources.getString(R.string.horaPlural);

        else if (intervalType.equals(resources.getString(R.string.minuto)))
            intervalType = resources.getString(R.string.minutoPlural);

        return intervalType;
    }

    static class TimeSinceBuilder {
        private Date init;
        private Date fin;
        Resources resources;

        TimeSinceBuilder setInit(Date init) {
            this.init = init;
            return this;
        }

        TimeSinceBuilder setFin(Date fin) {
            this.fin = fin;
            return this;
        }

        TimeSinceBuilder setResources(Resources resources) {
            this.resources = resources;
            return this;
        }

        TimeSince BuildTimeSince() {
            return new TimeSince(init, fin, resources);
        }
    }


}
