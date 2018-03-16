package com.hackathon2018.androidacademytlv.mapapp.Data;

import com.hackathon2018.androidacademytlv.mapapp.Models.Trip;

/**
 * Created by petero on 3/15/2018.
 */

public interface IItemAddedCallback<E> {
    void onItemAdded(E item);
}
