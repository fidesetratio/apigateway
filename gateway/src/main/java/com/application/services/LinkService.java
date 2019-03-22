package com.application.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.model.Link;
import com.application.repo.LinkRepository;

@Service
public class LinkService {

		@Autowired
		private LinkRepository linkRepository;
		
		
		public List<Link> getActiveLinks(){
			return linkRepository.findByActive(true);
		//	return linkRepository.findByActiveAndIsNew(true,true);
		}
		
		public List<Link> getNonActiveLinks(){
			//return linkRepository.findByActive(true);
			return linkRepository.findByActiveAndIsNew(false,true);
		}
		
		
		public List<Link> getOnlyNew(){
			//return linkRepository.findByActive(true);
			return linkRepository.findByIsNew(true);
		}
		
		
		public List<Link> getOnlyNewUrl(){
			//return linkRepository.findByActive(true);
			return linkRepository.findByIsNewUrl(true);
		}
		
		@Transactional
		public void update(List<Link> links) {
				int size = links.size();
				int counter = 0;
				List<Link> temp = new ArrayList<Link>();
				for(Link l:links) {
					temp.add(l);
					if ((counter + 1) % 500 == 0 || (counter + 1) == size) {
						linkRepository.saveAll(temp);
						temp.clear();
					}
					counter++;
				}
		}
}
