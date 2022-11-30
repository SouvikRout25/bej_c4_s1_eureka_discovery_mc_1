package com.example.UserTrackService.service;

import com.example.UserTrackService.domain.Track;
import com.example.UserTrackService.domain.User;
import com.example.UserTrackService.exception.TrackNotFoundException;
import com.example.UserTrackService.exception.UserAlreadyExistsException;
import com.example.UserTrackService.exception.UserNotFoundException;
import com.example.UserTrackService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        if(userRepository.findById(user.getUserId()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        return userRepository.save(user);
    }

    @Override
    public User addTrackForUser(String userId, Track track) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userId).get();
        if(user.getTrackList() == null){
            user.setTrackList(Arrays.asList(track));
        }else{
            List<Track> tracks = user.getTrackList();
            tracks.add(track);
            user.setTrackList(tracks);
        }
        return userRepository.save(user);
    }

    @Override
    public User deleteTrackFromUser(String userId, int trackId) throws UserNotFoundException, TrackNotFoundException {
        boolean result = false;
        if(userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userId).get();
        List<Track> tracks = user.getTrackList();
        result = tracks.removeIf(obj->obj.getTrackId()==trackId);
        if(!result)
        {
            throw new TrackNotFoundException();
        }
        user.setTrackList(tracks);
        return userRepository.save(user);
    }

    @Override
    public List<Track> getTrackForUser(String userId) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        return userRepository.findById(userId).get().getTrackList();
    }
    public User updateTrackForUser(String userId,Track track) throws UserNotFoundException {
        if(userRepository.findById(userId).isEmpty())
        {
            throw new UserNotFoundException();
        }
        User user = userRepository.findById(userId).get();
        List<Track> tracks = user.getTrackList();
        Iterator<Track> iterator = tracks.iterator();
        while (iterator.hasNext()){
            Track track1 = iterator.next();
            if(track1.getTrackId() == track.getTrackId()){
                track1.setTrackName(track.getTrackName());
                track1.setArtistName(track.getArtistName());
            }
        }
        user.setTrackList(tracks);
        return userRepository.save(user);
    }
}